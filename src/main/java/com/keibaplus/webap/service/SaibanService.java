package com.keibaplus.webap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.keibaplus.webap.entity.Saiban;
import com.keibaplus.webap.repository.SaibanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaibanService {
    private final SaibanRepository saibanRepository;

    private static final Logger logger = LoggerFactory.getLogger(SaibanService.class);

    @Transactional
    public String issueNextNo(String tableName) {
        try {
            Saiban saiban = saibanRepository.findByTableName(tableName)
                    .orElseThrow(() -> new IllegalArgumentException("採番テーブルの値が存在しません"));
            String prefix = saiban.getPrefix();
            String currentNo = saiban.getSaibanNo();
            String newNo;
            if (StringUtils.hasText(prefix)) {
                newNo = String.format("%08d", (Integer.parseInt(currentNo) + 1));
                saibanRepository.updateSaibanNo(newNo, tableName);
                return prefix + currentNo;
            } else {
                newNo = Integer.toString(Integer.parseInt(currentNo) + 1);
                saibanRepository.updateSaibanNo(newNo, tableName);
                return currentNo;
            }

        } catch (Exception e) {
            logger.error("採番処理失敗", e);
            throw new RuntimeException("採番処理でエラーが発生しました", e);
        }
    }
}
