package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.keibaplus.webap.entity.PasswordResetToken;

import java.time.LocalDateTime;

/**
 * パスワードリセットトークンテーブル用リポジトリ
 */
public interface PasswordResetTokenRepository extends ListCrudRepository<PasswordResetToken, Integer> {

        /**
         * パスワードリセットトークンテーブル取得
         * 
         * @param tokenHash トークン
         * @return パスワードリセットトークンテーブル取得結果
         */
        @Query("""
                            SELECT * FROM PASSWORD_RESET_TOKEN WHERE TOKEN_HASH = :tokenHash
                            AND USED_AT IS NULL
                            AND REVOKED_AT IS NULL
                            AND EXPIRES_AT > CURRENT_TIMESTAMP
                        """)
        Optional<PasswordResetToken> findByTokenHash(@Param("tokenHash") String tokenHash);

        /**
         * パスワードリセットトークンテーブル登録
         * 
         * @param resetTokenId パスワードリセットトークンID
         * @param userNo       ユーザー番号
         * @param tokenHash    トークン
         * @param expiresAt    有効期限
         * @param usedAt       使用日時
         * @param revokedAt    無効化日時
         * @param insDate      登録日時
         */
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

        /**
         * パスワードリセットトークンテーブル更新（使用日時）
         * 
         * @param usedAt       使用日時
         * @param resetTokenId パスワードリセットトークンID
         * @return 更新件数
         */
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

        /**
         * パスワードリセットトークンテーブル更新（無効化日時）
         * 
         * @param revokedAt 無効化日時
         * @param userNo    ユーザー番号
         * @return 更新件数
         */
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
