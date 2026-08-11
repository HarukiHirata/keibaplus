package com.keibaplus.webap.exception;

public class InvalidPasswordResetTokenException
        extends RuntimeException {

    public InvalidPasswordResetTokenException() {
        super("パスワード再設定リンクが無効または期限切れです");
    }
}
