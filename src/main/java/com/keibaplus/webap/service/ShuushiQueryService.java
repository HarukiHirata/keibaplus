package com.keibaplus.webap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.common.CurrentUserProvider;
import com.keibaplus.webap.dto.PageResponseDto;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.dto.ShuushiUpdateDto;
import com.keibaplus.webap.entity.Shuushi;
import com.keibaplus.webap.repository.ShuushiKenshuCourseRepository;
import com.keibaplus.webap.repository.ShuushiRepository;
import com.keibaplus.webap.repository.ShuushiSummaryRepository;

import java.nio.file.AccessDeniedException;

import lombok.RequiredArgsConstructor;

/**
 * 収支テーブル取得関連のService
 *
 */
@Service
@RequiredArgsConstructor
public class ShuushiQueryService {

    // Bean注入
    private final ShuushiRepository shuushiRepository;
    private final ShuushiSummaryRepository shuushiSummaryRepository;
    private final CurrentUserProvider currentUserProvider;
    private final ShuushiKenshuCourseRepository shuushiKenshuCourseRepository;

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(ShuushiQueryService.class);

    /**
     * 収支取得処理（収支一覧画面用・ページングあり）
     * 
     * @param dto  収支表示用DTO
     * @param page ページ番号
     * @param size 該当ページのレコード数
     * @return 収支テーブル取得結果
     */
    public PageResponseDto<ShuushiKenshuCourseDto> findAllShuushiByLoginUserWithPaging(ShuushiSearchDto dto, int page,
            int size) {
        // 不正なページ番号・ページサイズを修正
        int safePage = Math.max(page, CommonConst.MIN_PAGE_NUM);
        int safeSize = Math.min(Math.max(size, CommonConst.MIN_PAGE_SIZE), CommonConst.MAX_PAGE_SIZE);
        // セキュリティの関係上serviceで収支一覧取得対象のUserNoを指定
        dto.setUserNo(currentUserProvider.getLoginUserNo());
        // 削除されていないレコードを対象
        dto.setDelFlg(CommonConst.DEL_FLG_ACTIVE);

        // 該当ユーザーの収支テーブルの件数
        long totalElements = shuushiSummaryRepository.countByUserNo(dto);

        // ページ番号×ページサイズでスキップする件数を取得
        long offset = (long) safePage * safeSize;

        // 収支テーブルを検索
        List<ShuushiKenshuCourseDto> content = shuushiSummaryRepository.findByUserNoWithLimitAndOffset(dto, safeSize,
                offset);

        // 該当ユーザーの全件のページ数を計算して収支テーブル取得結果用（ページングあり）DTOをreturn
        int totalPages = totalElements == 0 ? 0 : (int) ((totalElements + safeSize - 1) / safeSize);

        return new PageResponseDto<>(content, safePage, safeSize, totalElements, totalPages);
    }

    /**
     * 収支取得処理（CSV出力用・ページングなし）
     * 
     * @param dto 収支検索用DTO
     * @return
     */
    public List<ShuushiKenshuCourseDto> findAllShuushiByLoginUserWithoutPaging(ShuushiSearchDto dto) {
        // セキュリティ上、ユーザー番号と削除フラグをServiceで設定して収支テーブルの検索結果をreturn
        dto.setUserNo(currentUserProvider.getLoginUserNo());
        dto.setDelFlg(CommonConst.DEL_FLG_ACTIVE);

        return shuushiSummaryRepository.findByUserNoWithoutLimitAndOffset(dto);
    }

    /**
     * 収支編集画面で表示するために収支を取得
     * 
     * @param shuushiNo 収支No
     * @return 収支更新用DTO（収支データ格納済み）
     */
    public ShuushiUpdateDto getShuushiByShuushiNo(Integer shuushiNo) {
        // 収支データ取得
        Shuushi shuushi;
        try {
            shuushi = shuushiRepository
                    .findByShuushiNo(shuushiNo, currentUserProvider.getLoginUserNo(),
                            CommonConst.DEL_FLG_ACTIVE)
                    .orElseThrow(() -> new AccessDeniedException("収支テーブルの値が存在しません"));
            // 収支更新用DTOのインスタンス作成
            ShuushiUpdateDto dto = new ShuushiUpdateDto();

            // レース番号をユーザーが未登録の場合は収支テーブルは0で格納している。
            // 上記理由によりレース番号だけ直接ではなく別の変数に格納してからDTOへセット
            Integer raceNo;
            if (shuushi.getRaceNo() == 0) {
                raceNo = null;
            } else {
                raceNo = shuushi.getRaceNo();
            }
            // 収支編集画面で表示できるように、取得したデータを収支更新用DTOへセットしてDTOをreturn
            dto.setShuushiNo(shuushi.getShuushiNo());
            dto.setUserNo(shuushi.getUserNo());
            dto.setRaceDate(shuushi.getRaceDate());
            dto.setCourseNo(shuushi.getCourseNo());
            dto.setRaceNo(raceNo);
            dto.setKenshuNo(shuushi.getKenshuNo());
            dto.setKounyuuKingaku(shuushi.getKounyuuKingaku());
            dto.setHaraimodoshi(shuushi.getHaraimodoshi());
            return dto;

        } catch (Exception e) {
            logger.error("収支取得失敗", e);
            throw new RuntimeException("収支取得処理でエラーが発生しました", e);
        }
    }

    /**
     * 収支削除画面で表示するために収支を取得
     * 
     * @param shuushiNo 収支No
     * @return 収支テーブル取得結果
     */
    public ShuushiKenshuCourseDto getShuushiByShuushiNoForDelete(Integer shuushiNo) {
        try {
            return shuushiKenshuCourseRepository
                    .findByShuushiNo(shuushiNo, currentUserProvider.getLoginUserNo(),
                            CommonConst.DEL_FLG_ACTIVE)
                    .orElseThrow(() -> new AccessDeniedException("収支テーブルの値が存在しません"));
        } catch (Exception e) {
            logger.error("収支取得失敗", e);
            throw new RuntimeException("収支取得処理でエラーが発生しました", e);
        }
    }

}
