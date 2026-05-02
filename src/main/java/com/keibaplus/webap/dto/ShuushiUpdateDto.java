package com.keibaplus.webap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShuushiUpdateDto {
    private Integer shuushiNo;

    private String userNo;

    @NotBlank(message = "レース日を入力してください")
    private String raceDate;

    private Integer courseNo;

    private Integer raceNo;

    private Integer kenshuNo;

    @NotNull(message = "購入金額を入力してください")
    private Integer kounyuuKingaku;

    @NotNull(message = "払い戻しを入力してください")
    private Integer haraimodoshi;

    public ShuushiUpdateDto() {
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
