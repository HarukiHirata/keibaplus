package com.keibaplus.webap.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AuthenticationEntryPointの実装（未認証アクセス時のログ出力・リダイレクト）
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    /**
     * commenceメソッドの実装
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        // ログ出力
        logger.info("未認証アクセス path={} query={}", request.getRequestURI(), request.getQueryString());

        // エラー画面へリダイレクト
        response.sendRedirect("/notlogin");
    }
}