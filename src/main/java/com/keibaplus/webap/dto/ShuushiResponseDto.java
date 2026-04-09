package com.keibaplus.webap.dto;

public class ShuushiResponseDto {

    private String userNo;

    private String raceDate;

    private Integer courseNo;

    private Integer raceNo;

    private Integer kenshuNo;

    private Integer kounyuuKingaku;

    private Integer haraimodoshi;

    public ShuushiResponseDto(String userNo, String raceDate, Integer courseNo,
            Integer raceNo, Integer kenshuNo, Integer kounyuuKingaku, Integer haraimodoshi) {
        this.userNo = userNo;
        this.raceDate = raceDate;
        this.courseNo = courseNo;
        this.raceNo = raceNo;
        this.kenshuNo = kenshuNo;
        this.kounyuuKingaku = kounyuuKingaku;
        this.haraimodoshi = haraimodoshi;
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

    public Integer getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(Integer courseNo) {
        this.courseNo = courseNo;
    }

    public Integer getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(Integer raceNo) {
        this.raceNo = raceNo;
    }

    public Integer getKenshuNo() {
        return kenshuNo;
    }

    public void setKenshuNo(Integer kenshuNo) {
        this.kenshuNo = kenshuNo;
    }

    public Integer getKounyuuKingaku() {
        return kounyuuKingaku;
    }

    public void setKounyuuKingaku(Integer kounyuuKingaku) {
        this.kounyuuKingaku = kounyuuKingaku;
    }

    public Integer getHaraimodoshi() {
        return haraimodoshi;
    }

    public void setHaraimodoshi(Integer haraimodoshi) {
        this.haraimodoshi = haraimodoshi;
    }

}
