package com.keibaplus.webap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.keibaplus.webap.dto.PasswordResetDto;
import com.keibaplus.webap.dto.PasswordResetRequestDto;
import com.keibaplus.webap.exception.InvalidPasswordResetTokenException;
import com.keibaplus.webap.service.PasswordResetService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

    /**
     * パスワードリセット申請画面。
     */
    @GetMapping("/password-reset-request")
    public String requestPage(Model model, HttpServletRequest request) {
        model.addAttribute("form", new PasswordResetRequestDto());
        logger.info("パスワードリセット申請画面表示 uri={}", request.getRequestURI());
        return "passwordResetRequest";
    }

    /**
     * リセットメール送信申請。
     */
    @PostMapping("/password-reset-request")
    public String request(
            @ModelAttribute("form") @Valid PasswordResetRequestDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "passwordResetRequest";
        }

        passwordResetService.requestPasswordReset(
                dto.getMailAddress());

        // メールアドレスの登録有無にかかわらず同じ画面を返す
        return "redirect:/password-reset-mail-sent";
    }

    /**
     * パスワード再設定用メール送信完了画面の表示
     * 
     * @param request HTTPサーブレットリクエスト情報
     * @return ユーザー削除成功画面のテンプレート
     */
    @GetMapping("/password-reset-mail-sent")
    public String passwordResetMailSentPage(HttpServletRequest request) {
        // ログ出力・テンプレートをreturn
        logger.info("パスワード再設定用メール送信完了画面表示 uri={}", request.getRequestURI());
        return "passwordResetMailSent";
    }

    /**
     * メール内リンクからパスワード再設定画面を表示。
     */
    @GetMapping("/password-reset")
    public String resetPage(
            @RequestParam("token") String token,
            Model model,
            HttpServletRequest request) {

        if (!passwordResetService.isUsableToken(token)) {
            return "passwordResetInvalid";
        }

        logger.info("パスワード再設定画面表示 uri={}", request.getRequestURI());

        PasswordResetDto dto = new PasswordResetDto();
        dto.setToken(token);
        model.addAttribute("form", dto);

        return "passwordReset";
    }

    /**
     * 新しいパスワードを登録。
     */
    @PostMapping("/password-reset")
    public String reset(
            @ModelAttribute("form") @Valid PasswordResetDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "passwordReset";
        }

        try {
            passwordResetService.resetPassword(
                    dto.getToken(),
                    dto.getPassword());
        } catch (InvalidPasswordResetTokenException e) {
            return "passwordResetInvalid";
        }

        return "redirect:/login?passwordReset";
    }
}