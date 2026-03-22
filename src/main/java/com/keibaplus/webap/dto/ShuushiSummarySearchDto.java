package com.keibaplus.webap.dto;

public class ShuushiSummarySearchDto {
    private String userNo;

    private String raceDateFrom;

    private String raceDateTo;

    private Integer kenshuNo;

    private Integer courseNo;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getRaceDateFrom() {
        return raceDateFrom;
    }

    public void setRaceDateFrom(String raceDateFrom) {
        this.raceDateFrom = raceDateFrom;
    }

    public String getRaceDateTo() {
        return raceDateTo;
    }

    public void setRaceDateTo(String raceDateTo) {
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
