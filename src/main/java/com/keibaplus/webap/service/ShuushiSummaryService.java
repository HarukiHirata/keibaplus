package com.keibaplus.webap.service;

import org.springframework.stereotype.Service;

import com.keibaplus.webap.dto.ShuushiSummaryDto;
import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.common.CurrentUserProvider;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.repository.ShuushiSummaryRepository;

import lombok.RequiredArgsConstructor;

/**
 * 回収率表示処理用のService
 */
@Service
@RequiredArgsConstructor
public class ShuushiSummaryService {
    // 必要なrepositoryのインスタンスを使用
    private final ShuushiSummaryRepository shuushiSummaryRepository;
    private final CurrentUserProvider currentUserProvider;

    /**
     * ユーザーが入力した条件に沿った購入金額と払い戻しの合計を取得（回収率の計算はjsで実施）
     * 
     * @param dto 収支検索用DTO
     * @return 購入金額・払い戻し合計
     */
    public ShuushiSummaryDto searchSummary(ShuushiSearchDto dto) {
        // セキュリティの関係上serviceで収支集計対象のUserNoを指定
        dto.setUserNo(currentUserProvider.getLoginUserNo());
        // 削除されていないレコードを対象
        dto.setDelFlg(CommonConst.DEL_FLG_ACTIVE);
        return shuushiSummaryRepository.searchSummary(dto);
    }
}
