package com.keibaplus.webap.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.keibaplus.webap.common.CurrentUserProvider;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー情報更新後に認証情報も更新するための処理
 * 
 */
@Service
@RequiredArgsConstructor
public class LoginSessionService {
    private final CustomUserDetailsService customUserDetailsService;
    private final CurrentUserProvider currentUserProvider;

    public void refreshLoginUser() {
        String userNo = currentUserProvider.getLoginUserNo();

        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

        // DBから更新後の情報を取得
        LoginUser updatedLoginUser = (LoginUser) customUserDetailsService.loadUserByUserNo(userNo);

        // 認証済みのAuthenticationを作成
        UsernamePasswordAuthenticationToken newAuthentication = UsernamePasswordAuthenticationToken
                .authenticated(
                        updatedLoginUser,
                        null,
                        updatedLoginUser.getAuthorities());

        // IPアドレスなどの認証詳細情報を引き継ぐ
        if (currentAuthentication != null) {
            newAuthentication.setDetails(currentAuthentication.getDetails());
        }

        // 現在の認証情報を差し替える
        SecurityContextHolder.getContext()
                .setAuthentication(newAuthentication);
    }

}
