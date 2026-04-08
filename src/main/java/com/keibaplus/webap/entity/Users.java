package com.keibaplus.webap.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("USERS")
public class Users {

    @Id
    @Column("USER_NO")
    private String userNo;

    @Column("USER_ID")
    private String userId;

    @Column("PASSWORD")
    private String password;

    @Column("MAIL_ADDRESS")
    private String mailAddress;

    @Column("DEL_FLG")
    private String delFlg;

    @Column("LAST_LOGIN_DATE")
    private LocalDateTime lastLoginDate;

    @Column("INS_DATE")
    private LocalDateTime insDate;

    @Column("UPD_DATE")
    private LocalDateTime updDate;

    public Users(String userNo, String userId, String password, String mailAddress, String delFlg,
            LocalDateTime lastLoginDate, LocalDateTime insDate, LocalDateTime updDate) {
        this.userNo = userNo;
        this.userId = userId;
        this.password = password;
        this.mailAddress = mailAddress;
        this.delFlg = delFlg;
        this.lastLoginDate = lastLoginDate;
        this.insDate = insDate;
        this.updDate = updDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public LocalDateTime getInsDate() {
        return insDate;
    }

    public void setInsDate(LocalDateTime insDate) {
        this.insDate = insDate;
    }

    public LocalDateTime getUpdDate() {
        return updDate;
    }

    public void setUpdDate(LocalDateTime updDate) {
        this.updDate = updDate;
    }

}
