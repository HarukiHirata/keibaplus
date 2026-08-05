package com.keibaplus.webap.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keibaplus.webap.dto.ShuushiSummaryDto;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.service.ShuushiSummaryService;
import com.keibaplus.webap.service.ShuushiQueryService;

import lombok.RequiredArgsConstructor;

/**
 * 収支集計処理関係のコントローラー
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shuushisummary")
public class SummaryApiController {

    // 収支集計処理のためにShuushiSummaryServiceのインスタンスを使用
    private final ShuushiSummaryService shuushiSummaryService;

    // 収支集計処理のためにShuushiServiceのインスタンスを使用
    private final ShuushiQueryService shuushiQueryService;

    /**
     * 収支集計処理
     * 
     * @param dto 収支検索用DTO
     * @return ShuushiSummaryServiceの収支集計結果
     */
    @PostMapping("/search")
    public ShuushiSummaryDto search(@RequestBody ShuushiSearchDto dto) {
        // ShuushiSummaryServiceの収支集計処理の結果をreturn
        return shuushiSummaryService.searchSummary(dto);
    }

    /**
     * 収支一覧取得処理
     * 
     * @param dto 収支検索用DTO
     * @return ShuushiServiceの収支一覧取得結果
     */
    @PostMapping("/itiran")
    public List<ShuushiKenshuCourseDto> itiran(@RequestBody ShuushiSearchDto dto) {
        // ShuushiServiceの収支一覧取得処理の結果をreturn
        return shuushiQueryService.findAllShushiByLoginUser(dto);
    }
}
