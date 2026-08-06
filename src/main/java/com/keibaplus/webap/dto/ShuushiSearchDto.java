package com.keibaplus.webap.dto;

import java.time.LocalDate;

public class ShuushiSearchDto {
    private String userNo;

    private String delFlg;

    private LocalDate raceDateFrom;

    private LocalDate raceDateTo;

    private Integer kenshuNo;

    private Integer courseNo;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public LocalDate getRaceDateFrom() {
        return raceDateFrom;
    }

    public void setRaceDateFrom(LocalDate raceDateFrom) {
        this.raceDateFrom = raceDateFrom;
    }

    public LocalDate getRaceDateTo() {
        return raceDateTo;
    }

    public void setRaceDateTo(LocalDate raceDateTo) {
        this.raceDateTo = raceDateTo;
    }

    public Integer getKenshuNo() {
        return kenshuNo;
    }

    public void setKenshuNo(Integer kenshuNo) {
        this.kenshuNo = kenshuNo;
    }

    public Integer getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(Integer courseNo) {
        this.courseNo = courseNo;
    }

}
