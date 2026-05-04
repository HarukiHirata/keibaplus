package com.keibaplus.webap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShuushiRegisterDto {

    @NotBlank(message = "レース日を入力してください")
    private String raceDate;

    private Integer courseNo;

    private Integer raceNo;

    private Integer kenshuNo;

    @NotNull(message = "購入金額を入力してください")
    private Integer kounyuuKingaku;

    @NotNull(message = "払い戻しを入力してください")
    private Integer haraimodoshi;

    public ShuushiRegisterDto() {
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
