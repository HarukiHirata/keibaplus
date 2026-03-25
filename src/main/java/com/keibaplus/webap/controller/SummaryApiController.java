package com.keibaplus.webap.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keibaplus.webap.dto.ShuushiSummaryDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.service.ShuushiSummaryService;
import com.keibaplus.webap.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shuushisummary")
public class SummaryApiController {

    private final ShuushiSummaryService shuushiSummaryService;

    private final UsersService usersService;

    @PostMapping("/search")
    public ShuushiSummaryDto search(@RequestBody ShuushiSearchDto dto) {
        dto.setUserNo(usersService.getLoginUserNo());
        return shuushiSummaryService.searchSummary(dto);
    }
}
