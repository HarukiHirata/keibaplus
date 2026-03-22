package com.keibaplus.webap.dto;

public class ShuushiSummaryDto {
    private Integer totalKounyuuKingaku;

    private Integer totalHaraimodoshi;

    public ShuushiSummaryDto(Integer totalKounyuuKingaku, Integer totalHaraimodoshi) {
        this.totalKounyuuKingaku = totalKounyuuKingaku;
        this.totalHaraimodoshi = totalHaraimodoshi;
    }

    public Integer getTotalKounyuuKingaku() {
        return totalKounyuuKingaku;
    }

    public void setTotalKounyuuKingaku(Integer totalKounyuuKingaku) {
        this.totalKounyuuKingaku = totalKounyuuKingaku;
    }

    public Integer getTotalHaraimodoshi() {
        return totalHaraimodoshi;
    }

    public void setTotalHaraimodoshi(Integer totalHaraimodoshi) {
        this.totalHaraimodoshi = totalHaraimodoshi;
    }
}
