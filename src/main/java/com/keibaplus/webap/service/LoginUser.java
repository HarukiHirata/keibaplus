package com.keibaplus.webap.service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.*;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Collections;

public class LoginUser implements UserDetails {
    private String userNo;
    private String userId;
    private String mailAddress;
    private LocalDateTime lastLoginDate;
    private String password;

    public LoginUser(String userNo,
            String userId,
            String mailAddress,
            LocalDateTime lastLoginDate,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        this.userNo = userNo;
        this.userId = userId;
        this.mailAddress = mailAddress;
        this.lastLoginDate = lastLoginDate;
        this.password = password;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
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

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Override
    public String getUsername() {
        return userNo;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
