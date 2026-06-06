package com.keibaplus.webap.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.keibaplus.webap.service.LoginUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * LogoutSuccessHandlerの実装（ログアウト成功時のログ出力・リダイレクト）
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    /**
     * onLogoutSuccessメソッドの実装
     */
    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            // Spring Securityの認証情報を取得・ユーザー名以外のデータを取得するためにPrincipalを格納してそこからユーザー情報を取得
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            // ログ出力
            logger.info("ログアウト成功 userNo={}", loginUser.getUserNo());
        } else {
            // ログ出力
            logger.info("ログアウト成功 userNo=unknown");
        }

        // ログイン画面へリダイレクト（ログアウト成功メッセージ表示）
        response.sendRedirect("/login?logout");
    }
}