package com.keibaplus.webap.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AuthenticationFailureHandlerの実装（ログイン失敗時のログ出力・リダイレクト）
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    /**
     * onAuthenticationFailureメソッドの実装
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        // ログ出力
        logger.warn("ログイン失敗 reason={}", exception.getMessage());

        // ログイン画面へのリダイレクト（エラーメッセージ表示）
        response.sendRedirect("/login?error");
    }
}