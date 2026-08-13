package com.keibaplus.webap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.keibaplus.webap.entity.Saiban;
import com.keibaplus.webap.repository.SaibanRepository;

import lombok.RequiredArgsConstructor;

/**
 * 採番テーブル関連のService
 */
@Service
@RequiredArgsConstructor
public class SaibanService {

    // Bean注入
    private final SaibanRepository saibanRepository;

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(SaibanService.class);

    /**
     * 採番テーブル値取得・更新処理（更新を忘れないように取得と更新を同じ処理で完結）
     * 
     * @param tableName
     * @return 採番テーブルデータ（更新前）
     */
    @Transactional
    public String issueNextNo(String tableName) {
        try {
            // 採番テーブルの値を取得
            Saiban saiban = saibanRepository.findByTableName(tableName)
                    .orElseThrow(() -> new IllegalArgumentException("採番テーブルの値が存在しません"));
            String prefix = saiban.getPrefix();
            String currentNo = saiban.getSaibanNo();
            String newNo;

            // PREFIXカラムがnullであれば設定値のみをreturn、
            // そうでなければprefixカラムとsaibannoカラムの設定値を合わせたものをreturn
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
