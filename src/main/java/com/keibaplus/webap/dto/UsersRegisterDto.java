package com.keibaplus.webap.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ユーザー登録用DTO
 */
public class UsersRegisterDto {

    @NotBlank(message = "ユーザーIDを入力してください")
    @Size(min = 8, max = 30, message = "ユーザーIDは8文字以上30文字以内で入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ユーザーIDは半角英数字で入力してください")
    private String userId;

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスを正しい形式で入力してください")
    private String mailAddress;

    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 8, max = 64, message = "パスワードは8文字以上64文字以内で入力してください")
    private String password;

    @NotBlank(message = "確認用パスワードを入力してください")
    @Size(min = 8, max = 64, message = "パスワードは8文字以上64文字以内で入力してください")
    private String passwordConfirm;

    public UsersRegisterDto() {

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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @AssertTrue(message = "パスワードと確認用パスワードが一致していません")
    public boolean isPasswordMatch() {
        return getPassword().equals(getPasswordConfirm());
    }
}
