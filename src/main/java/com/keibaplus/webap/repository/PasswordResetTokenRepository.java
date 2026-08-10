package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.keibaplus.webap.entity.PasswordResetToken;

import java.time.LocalDateTime;
import java.util.List;

public interface PasswordResetTokenRepository extends ListCrudRepository<PasswordResetToken, Integer> {
    @Query("""
                SELECT * FROM PASSWORD_RESET_TOKEN WHERE TOKEN_HASH = :tokenHash
                AND USED_AT IS NULL
                AND REVOKED_AT IS NULL
                AND EXPIRES_AT > CURRENT_TIMESTAMP
            """)
    Optional<PasswordResetToken> findByTokenHash(@Param("tokenHash") String tokenHash);

    @Modifying
    @Query("""
            INSERT INTO PASSWORD_RESET_TOKEN
            (RESET_TOKEN_ID, USER_NO, TOKEN_HASH, EXPIRES_AT, USED_AT, REVOKED_AT, INS_DATE)
            VALUES
            (:resetTokenId, :userNo, :tokenHash, :expiresAt, :usedAt, :revokedAt, :insDate)
                """)
    void registerPasswordResetToken(@Param("resetTokenId") int resetTokenId,
            @Param("userNo") String userNo,
            @Param("tokenHash") String tokenHash,
            @Param("expiresAt") LocalDateTime expiresAt,
            @Param("usedAt") LocalDateTime usedAt,
            @Param("revokedAt") LocalDateTime revokedAt,
            @Param("insDate") LocalDateTime insDate);

    @Modifying
    @Query("""
            UPDATE PASSWORD_RESET_TOKEN
            SET USED_AT = :usedAt
            WHERE RESET_TOKEN_ID = :resetTokenId
            AND USED_AT IS NULL
            AND REVOKED_AT IS NULL
            AND EXPIRES_AT > CURRENT_TIMESTAMP
                    """)
    int updatePasswordResetTokenUsedAt(@Param("usedAt") LocalDateTime usedAt,
            @Param("resetTokenId") int resetTokenId);

    @Modifying
    @Query("""
            UPDATE PASSWORD_RESET_TOKEN
            SET REVOKED_AT = :revokedAt
            WHERE USER_NO = :userNo
            AND USED_AT IS NULL
            AND REVOKED_AT IS NULL
                    """)
    int updatePasswordResetTokenRevokedAt(@Param("revokedAt") LocalDateTime revokedAt,
            @Param("userNo") String userNo);

}
