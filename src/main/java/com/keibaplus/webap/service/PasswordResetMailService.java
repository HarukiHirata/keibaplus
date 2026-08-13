package com.keibaplus.webap.service;

/**
 * パスワード再設定用メール送信処理（インターフェース）
 */
public interface PasswordResetMailService {

    void sendResetLink(String mailAddress, String rawToken);
}
