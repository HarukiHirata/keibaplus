package com.keibaplus.webap.dto;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

public class UsersResponseDto {
    private String userNo;
    private String userId;
    private String mailAddress;
    private LocalDateTime lastLoginDate;

    public UsersResponseDto(String userNo, String userId, String mailAddress, LocalDateTime lastLoginDate) {
        this.userNo = userNo;
        this.userId = userId;
        this.mailAddress = mailAddress;
        this.lastLoginDate = lastLoginDate;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
