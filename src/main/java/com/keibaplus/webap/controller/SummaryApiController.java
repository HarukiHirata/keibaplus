package com.keibaplus.webap.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keibaplus.webap.dto.ShuushiSummaryDto;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.service.ShuushiService;
import com.keibaplus.webap.service.ShuushiSummaryService;
import com.keibaplus.webap.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shuushisummary")
public class SummaryApiController {

    private final ShuushiSummaryService shuushiSummaryService;

    private final UsersService usersService;

    private final ShuushiService shuushiService;

    @PostMapping("/search")
    public ShuushiSummaryDto search(@RequestBody ShuushiSearchDto dto) {
        dto.setUserNo(usersService.getLoginUserNo());
        return shuushiSummaryService.searchSummary(dto);
    }

    @PostMapping("/itiran")
    public List<ShuushiKenshuCourseDto> itiran(@RequestBody ShuushiSearchDto dto) {
        dto.setUserNo(usersService.getLoginUserNo());
        return shuushiService.findAllShushiByLoginUser(dto);
    }
}
