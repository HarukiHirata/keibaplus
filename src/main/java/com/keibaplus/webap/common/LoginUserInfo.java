package com.keibaplus.webap.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import com.keibaplus.webap.service.LoginUser;

/**
 * ログインユーザー情報を取得するための共通処理
 *
 */
public class LoginUserInfo {
    /**
     * ログインユーザー情報取得処理
     * 
     * @return ログインユーザー情報
     */
    private static LoginUser getLoginUser() {
        // Spring Securityの認証情報を取得
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // ユーザー名以外のデータを取得するためにPrincipalを格納してそこからユーザー情報を取得
        LoginUser loginUser = (LoginUser) auth.getPrincipal();
        return loginUser;
    }

    /**
     * ログインユーザー番号取得処理
     * 
     * @return getLoginUser()のユーザー番号
     */
    public static String getLoginUserNo() {
        return getLoginUser().getUserNo();
    }

    /**
     * ログインユーザーID取得処理
     * 
     * @return getLoginUser()のユーザーID
     */
    public static String getLoginUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * ログインユーザーメールアドレス取得処理
     * 
     * @return getLoginUser()のメールアドレス
     */
    public static String getLoginUserMailAddress() {
        return getLoginUser().getMailAddress();
    }

}
