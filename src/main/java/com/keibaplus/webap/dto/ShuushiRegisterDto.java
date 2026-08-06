package com.keibaplus.webap.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * 収支登録用DTO
 */
public class ShuushiRegisterDto {

    @NotNull(message = "レース日を入力してください")
    private LocalDate raceDate;

    private Integer courseNo;

    private Integer raceNo;

    private Integer kenshuNo;

    @PositiveOrZero(message = "0以上の数値を入力してください")
    @NotNull(message = "購入金額を入力してください")
    private Integer kounyuuKingaku;

    @PositiveOrZero(message = "0以上の数値を入力してください")
    @NotNull(message = "払い戻しを入力してください")
    private Integer haraimodoshi;

    public ShuushiRegisterDto() {
    }

    public LocalDate getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(LocalDate raceDate) {
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
