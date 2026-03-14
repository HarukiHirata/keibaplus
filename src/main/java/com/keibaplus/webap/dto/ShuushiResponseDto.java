package com.keibaplus.webap.dto;

public class ShuushiResponseDto {

    private String userNo;

    private String kenshuNo;

    private String raceDate;

    private String courseNo;

    private String raceNo;

    private int kounyuuKingaku;

    private int haraimodoshi;

    public ShuushiResponseDto(String userNo, String kenshuNo, String raceDate, String courseNo,
            String raceNo, int kounyuuKingaku, int haraimodoshi) {
        this.userNo = userNo;
        this.kenshuNo = kenshuNo;
        this.raceDate = raceDate;
        this.courseNo = courseNo;
        this.raceNo = raceNo;
        this.kounyuuKingaku = kounyuuKingaku;
        this.haraimodoshi = haraimodoshi;
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

}
