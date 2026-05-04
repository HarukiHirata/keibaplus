package com.keibaplus.webap.dto;

public class UsersResponseDto {
    private String userNo;
    private String userId;
    private String mailAddress;

    public UsersResponseDto(String userNo, String userId, String mailAddress) {
        this.userNo = userNo;
        this.userId = userId;
        this.mailAddress = mailAddress;
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
}
