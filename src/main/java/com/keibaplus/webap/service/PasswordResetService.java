package com.keibaplus.webap.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.entity.PasswordResetToken;
import com.keibaplus.webap.entity.Users;
import com.keibaplus.webap.repository.PasswordResetTokenRepository;
import com.keibaplus.webap.repository.UsersRepository;
import com.keibaplus.webap.exception.InvalidPasswordResetTokenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private static final int TOKEN_BYTES = 32;
    private static final long TOKEN_VALID_MINUTES = 30;

    private final SecureRandom secureRandom = new SecureRandom();

    private final UsersRepository usersRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetMailService mailService;
    private final SaibanService saibanService;

    /**
     * 再設定リンクを発行する。
     *
     * メールアドレスが存在しない場合も例外を返さず、
     * Controllerから見た結果を同じにする。
     */
    @Transactional
    public void requestPasswordReset(String mailAddress) {

        Optional<Users> userOptional = usersRepository.findByMailAddress(
                mailAddress,
                CommonConst.DEL_FLG_ACTIVE);

        if (userOptional.isEmpty()) {
            return;
        }

        Users user = userOptional.get();
        LocalDateTime now = LocalDateTime.now();

        // 過去の未使用リンクを無効化
        tokenRepository.updatePasswordResetTokenRevokedAt(now, user.getUserNo());

        String rawToken = generateToken();
        String tokenHash = hashToken(rawToken);
        int registerPasswordResetTokenNo = Integer
                .parseInt(saibanService.issueNextNo(CommonConst.PASSWORD_RESET_TOKEN_TABLE_NAME));

        tokenRepository.registerPasswordResetToken(
                registerPasswordResetTokenNo,
                user.getUserNo(),
                tokenHash,
                now.plusMinutes(TOKEN_VALID_MINUTES),
                null,
                null,
                now);

        // メールには生トークンを含める
        mailService.sendResetLink(user.getMailAddress(), rawToken);
    }

    @Transactional(readOnly = true)
    public boolean isUsableToken(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            return false;
        }

        return tokenRepository
                .findByTokenHash(hashToken(rawToken))
                .isPresent();
    }

    /**
     * トークンを使用し、新しいパスワードへ変更する。
     */
    @Transactional
    public void resetPassword(String rawToken, String newPassword) {

        String tokenHash = hashToken(rawToken);

        PasswordResetToken resetToken = tokenRepository
                .findByTokenHash(tokenHash)
                .orElseThrow(InvalidPasswordResetTokenException::new);

        LocalDateTime now = LocalDateTime.now();

        // 条件付きUPDATEによって同時リクエストの二重使用を防止
        int usedCount = tokenRepository.updatePasswordResetTokenUsedAt(
                now,
                resetToken.getResetTokenId());

        if (usedCount != 1) {
            throw new InvalidPasswordResetTokenException();
        }

        int updatedCount = usersRepository.updatePassword(
                resetToken.getUserNo(),
                CommonConst.DEL_FLG_ACTIVE,
                passwordEncoder.encode(newPassword),
                now,
                now);

        if (updatedCount != 1) {
            throw new InvalidPasswordResetTokenException();
        }

        // 同一ユーザーに残っている他のリンクも無効化
        tokenRepository.updatePasswordResetTokenRevokedAt(now,
                resetToken.getUserNo());
    }

    private String generateToken() {
        byte[] bytes = new byte[TOKEN_BYTES];
        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    rawToken.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256を利用できません", e);
        }
    }
}
