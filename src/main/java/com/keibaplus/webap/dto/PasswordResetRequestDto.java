package com.keibaplus.webap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public class PasswordResetRequestDto {

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスを正しい形式で入力してください")
    private String mailAddress;

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}