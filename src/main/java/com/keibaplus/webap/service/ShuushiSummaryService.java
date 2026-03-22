package com.keibaplus.webap.service;

import org.springframework.stereotype.Service;

import com.keibaplus.webap.dto.ShuushiSummaryDto;
import com.keibaplus.webap.dto.ShuushiSummarySearchDto;
import com.keibaplus.webap.repository.ShuushiSummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShuushiSummaryService {
    private final ShuushiSummaryRepository shuushiSummaryRepository;

    public ShuushiSummaryDto searchSummary(ShuushiSummarySearchDto dto) {
        return shuushiSummaryRepository.searchSummary(dto);
    }
}
