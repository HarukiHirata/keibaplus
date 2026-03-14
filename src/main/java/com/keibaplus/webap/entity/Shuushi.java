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

    @Column("KENSHU_NO")
    private String kenshuNo;

    @Column("RACE_DATE")
    private String raceDate;

    @Column("COURSE_NO")
    private String courseNo;

    @Column("RACE_NO")
    private String raceNo;

    @Column("KOUNYUU_KINGAKU")
    private int kounyuuKingaku;

    @Column("HARAIMODOSHI")
    private int haraimodoshi;

    @Column("INS_DATE")
    private LocalDateTime insDate;

    @Column("UPD_DATE")
    private LocalDateTime updDate;

    public Shuushi(int shuushiNo, String userNo, String kenshuNo, String raceDate, String courseNo,
            String raceNo, int kounyuuKingaku, int haraimodoshi, LocalDateTime insDate, LocalDateTime updDate) {
        this.shuushiNo = shuushiNo;
        this.userNo = userNo;
        this.kenshuNo = kenshuNo;
        this.raceDate = raceDate;
        this.courseNo = courseNo;
        this.raceNo = raceNo;
        this.kounyuuKingaku = kounyuuKingaku;
        this.haraimodoshi = haraimodoshi;
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

    public String getKenshuNo() {
        return kenshuNo;
    }

    public void setKenshuNo(String kenshuNo) {
        this.kenshuNo = kenshuNo;
    }

    public String getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(String raceDate) {
        this.raceDate = raceDate;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(String raceNo) {
        this.raceNo = raceNo;
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
