package com.keibaplus.webap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.keibaplus.webap.entity.Saiban;
import com.keibaplus.webap.repository.SaibanRepository;

@Service
public class SaibanService {
    private final SaibanRepository saibanRepository;

    private static final Logger logger = LoggerFactory.getLogger(ShuushiService.class);

    public SaibanService(SaibanRepository saibanRepository) {
        this.saibanRepository = saibanRepository;
    }

    public String getNewNo(String tableName) {
        try {
            Saiban saiban = saibanRepository.findByTableName(tableName)
                    .orElseThrow(() -> new IllegalArgumentException("採番テーブルの値が存在しません"));
            if (!saiban.getPrefix().isEmpty()) {
                return saiban.getPrefix() + saiban.getSaibanNo();
            } else {
                return saiban.getSaibanNo();
            }

        } catch (Exception e) {
            logger.error("採番処理失敗", e);
            throw new RuntimeException("採番処理でエラーが発生しました", e);
        }
    }

    public void updateSaibanNo(String tableName) {
        Saiban saiban = saibanRepository.findByTableName(tableName)
                .orElseThrow(() -> new IllegalArgumentException("採番テーブルの値が存在しません"));

        String prefix = saiban.getPrefix();

        int currentSaibanNo = Integer.parseInt(saiban.getSaibanNo());

        if (!prefix.isEmpty()) {
            String newSaibanNo = String.format("%08d", (currentSaibanNo + 1));
            saibanRepository.updateSaibanNo(newSaibanNo, tableName);
        } else {
            String newSaibanNo = Integer.toString(currentSaibanNo + 1);
            saibanRepository.updateSaibanNo(newSaibanNo, tableName);
        }
    }
}
