package com.keibaplus.webap.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * パスワードリセットトークンテーブル用エンティティ
 * PasswordResetToken
 */
@Table("password_reset_token")
public class PasswordResetToken {

    @Id
    @Column("reset_token_id")
    private int resetTokenId;

    @Column("user_no")
    private String userNo;

    @Column("token_hash")
    private String tokenHash;

    @Column("expires_at")
    private LocalDateTime expiresAt;

    @Column("used_at")
    private LocalDateTime usedAt;

    @Column("revoked_at")
    private LocalDateTime revokedAt;

    @Column("ins_date")
    private LocalDateTime insDate;

    public PasswordResetToken(int resetTokenId, String userNo, String tokenHash,
            LocalDateTime expiresAt, LocalDateTime usedAt, LocalDateTime revokedAt, LocalDateTime insDate) {
        this.resetTokenId = resetTokenId;
        this.userNo = userNo;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.usedAt = usedAt;
        this.revokedAt = revokedAt;
        this.insDate = insDate;
    }

    public int getResetTokenId() {
        return resetTokenId;
    }

    public void setResetTokenId(int resetTokenId) {
        this.resetTokenId = resetTokenId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }

    public LocalDateTime getInsDate() {
        return insDate;
    }

    public void setInsDate(LocalDateTime insDate) {
        this.insDate = insDate;
    }

}
