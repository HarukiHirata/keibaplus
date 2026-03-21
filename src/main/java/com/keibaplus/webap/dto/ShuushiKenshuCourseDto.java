package com.keibaplus.webap.dto;

public class ShuushiKenshuCourseDto {
    private Integer shuushiNo;

    private String userNo;

    private String kenshuName;

    private String raceDate;

    private String courseName;

    private Integer raceNo;

    private Integer kounyuuKingaku;

    private Integer haraimodoshi;

    public ShuushiKenshuCourseDto() {
    }

    public ShuushiKenshuCourseDto(Integer shuushiNo, String userNo, String kenshuName, String raceDate,
            String courseName,
            Integer raceNo, Integer kounyuuKingaku, Integer haraimodoshi) {
        this.shuushiNo = shuushiNo;
        this.userNo = userNo;
        this.kenshuName = kenshuName;
        this.raceDate = raceDate;
        this.courseName = courseName;
        this.raceNo = raceNo;
        this.kounyuuKingaku = kounyuuKingaku;
        this.haraimodoshi = haraimodoshi;
    }

    public Integer getShuushiNo() {
        return shuushiNo;
    }

    public void setShuushiNo(Integer shuushiNo) {
        this.shuushiNo = shuushiNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getKenshuName() {
        return kenshuName;
    }

    public void setKenshuName(String kenshuName) {
        this.kenshuName = kenshuName;
    }

    public String getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(String raceDate) {
        this.raceDate = raceDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getRaceNo() {
        return raceNo;
    }

    public void setRaceNo(Integer raceNo) {
        this.raceNo = raceNo;
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
