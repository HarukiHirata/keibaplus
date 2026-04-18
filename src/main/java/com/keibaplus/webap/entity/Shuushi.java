package com.keibaplus.webap.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("SHUUSHI")
public class Shuushi {

    @Id
    @Column("SHUUSHI_NO")
    private int shuushiNo;

    @Column("USER_NO")
    private String userNo;

    @Column("RACE_DATE")
    private String raceDate;

    @Column("COURSE_NO")
    private int courseNo;

    @Column("RACE_NO")
    private int raceNo;

    @Column("KENSHU_NO")
    private int kenshuNo;

    @Column("KOUNYUU_KINGAKU")
    private int kounyuuKingaku;

    @Column("HARAIMODOSHI")
    private int haraimodoshi;

    @Column("DEL_FLG")
    private String delFlg;

    @Column("INS_DATE")
    private LocalDateTime insDate;

    @Column("UPD_DATE")
    private LocalDateTime updDate;

    public Shuushi(int shuushiNo, String userNo, String raceDate, int courseNo, int raceNo,
            int kenshuNo, int kounyuuKingaku, int haraimodoshi, String delFlg, LocalDateTime insDate,
            LocalDateTime updDate) {
        this.shuushiNo = shuushiNo;
        this.userNo = userNo;
        this.raceDate = raceDate;
        this.courseNo = courseNo;
        this.raceNo = raceNo;
        this.kenshuNo = kenshuNo;
        this.kounyuuKingaku = kounyuuKingaku;
        this.haraimodoshi = haraimodoshi;
        this.delFlg = delFlg;
        this.insDate = insDate;
        this.updDate = updDate;
    }

    public int getShuushiNo() {
        return shuushiNo;
    }

    public void setShuushiNo(int shuushiNo) {
        this.shuushiNo = shuushiNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(String raceDate) {
        this.raceDate = raceDate;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    public int getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(int raceNo) {
        this.raceNo = raceNo;
    }

    public int getKenshuNo() {
        return kenshuNo;
    }

    public void setKenshuNo(int kenshuNo) {
        this.kenshuNo = kenshuNo;
    }

    public int getKounyuuKingaku() {
        return kounyuuKingaku;
    }

    public void setKounyuuKingaku(int kounyuuKingaku) {
        this.kounyuuKingaku = kounyuuKingaku;
    }

    public int getHaraimodoshi() {
        return haraimodoshi;
    }

    public void setHaraimodoshi(int haraimodoshi) {
        this.haraimodoshi = haraimodoshi;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
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
