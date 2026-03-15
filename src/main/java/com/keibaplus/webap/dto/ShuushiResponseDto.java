package com.keibaplus.webap.dto;

public class ShuushiResponseDto {

    private String userNo;

    private int kenshuNo;

    private String raceDate;

    private int courseNo;

    private int raceNo;

    private int kounyuuKingaku;

    private int haraimodoshi;

    public ShuushiResponseDto(String userNo, int kenshuNo, String raceDate, int courseNo,
            int raceNo, int kounyuuKingaku, int haraimodoshi) {
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

    public int getKenshuNo() {
        return kenshuNo;
    }

    public void setKenshuNo(int kenshuNo) {
        this.kenshuNo = kenshuNo;
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
