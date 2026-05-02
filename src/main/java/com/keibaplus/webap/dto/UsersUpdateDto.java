package com.keibaplus.webap.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsersUpdateDto {
    private String userNo;

    @NotBlank(message = "ユーザーIDを入力してください")
    @Size(min = 8, max = 20, message = "ユーザーIDは8文字以上20文字以内で入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ユーザーIDは半角英数字で入力してください")
    private String userId;

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスを正しい形式で入力してください")
    private String mailAddress;

    private String password;

    public UsersUpdateDto() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
