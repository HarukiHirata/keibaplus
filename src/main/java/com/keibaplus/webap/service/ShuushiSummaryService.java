package com.keibaplus.webap.service;

import org.springframework.stereotype.Service;

import com.keibaplus.webap.dto.ShuushiSummaryDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.repository.ShuushiSummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShuushiSummaryService {
    private final ShuushiSummaryRepository shuushiSummaryRepository;

    public ShuushiSummaryDto searchSummary(ShuushiSearchDto dto) {
        return shuushiSummaryRepository.searchSummary(dto);
    }
}
