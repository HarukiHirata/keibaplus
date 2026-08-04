package com.keibaplus.webap.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;

import com.keibaplus.webap.service.LoginUser;

/**
 * ログインユーザー情報を取得するための共通処理
 *
 */
@Component
public class CurrentUserProvider {
    /**
     * ログインユーザー情報取得処理
     * 
     * @return ログインユーザー情報
     */
    private LoginUser getCurrentLoginUser() {
        // Spring Securityの認証情報を取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null
                || !auth.isAuthenticated()
                || !(auth.getPrincipal() instanceof LoginUser loginUser)) {
            throw new AuthenticationCredentialsNotFoundException(
                    "ログインユーザーが存在しません");
        }

        return loginUser;
    }

    /**
     * ログインユーザー番号取得処理
     * 
     * @return getLoginUser()のユーザー番号
     */
    public String getLoginUserNo() {
        return getCurrentLoginUser().getUserNo();
    }

    /**
     * ログインユーザーID取得処理
     * 
     * @return getLoginUser()のユーザーID
     */
    public String getLoginUserId() {
        return getCurrentLoginUser().getUserId();
    }

    /**
     * ログインユーザーメールアドレス取得処理
     * 
     * @return getLoginUser()のメールアドレス
     */
    public String getLoginUserMailAddress() {
        return getCurrentLoginUser().getMailAddress();
    }

}
