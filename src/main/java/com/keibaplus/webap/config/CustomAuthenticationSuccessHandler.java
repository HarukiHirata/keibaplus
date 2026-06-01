package com.keibaplus.webap.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.keibaplus.webap.service.LoginUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AuthenticationSuccessHandlerの実装（ログイン成功時のログ出力・リダイレクト）
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    /**
     * onAuthenticationSuccessメソッドの実装
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Spring Securityの認証情報を取得・ユーザー名以外のデータを取得するためにPrincipalを格納してそこからユーザー情報を取得
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // ログ出力
        logger.info("ログイン成功 userNo={}", loginUser.getUserNo());
        // トップ画面へリダイレクト
        response.sendRedirect("/top");
    }
}