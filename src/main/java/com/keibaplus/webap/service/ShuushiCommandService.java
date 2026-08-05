package com.keibaplus.webap.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keibaplus.webap.entity.Saiban;
import com.keibaplus.webap.entity.Shuushi;
import com.keibaplus.webap.entity.Kenshu;
import com.keibaplus.webap.entity.Course;
import com.keibaplus.webap.repository.ShuushiRepository;
import com.keibaplus.webap.repository.KenshuRepository;
import com.keibaplus.webap.repository.CourseRepository;
import com.keibaplus.webap.repository.SaibanRepository;
import com.keibaplus.webap.repository.ShuushiKenshuCourseRepository;
import com.keibaplus.webap.repository.ShuushiSummaryRepository;
import com.keibaplus.webap.dto.ShuushiRegisterDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.dto.ShuushiUpdateDto;
import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.common.CurrentUserProvider;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

/**
 * 収支テーブル登録・更新・削除のService
 */
@Service
public class ShuushiCommandService {
        // 必要なrepositoryのインスタンスを使用
        private final ShuushiRepository shuushiRepository;
        private final SaibanService saibanService;
        private final CurrentUserProvider currentUserProvider;
        // ロガーの定義
        private static final Logger logger = LoggerFactory.getLogger(ShuushiCommandService.class);

        // コンストラクタ
        public ShuushiCommandService(ShuushiRepository shuushiRepository, SaibanService saibanService,
                        CurrentUserProvider currentUserProvider) {
                this.shuushiRepository = shuushiRepository;
                this.saibanService = saibanService;
                this.currentUserProvider = currentUserProvider;
        }

        /**
         * 収支登録処理
         * 
         * @param dto 収支登録用DTO
         */
        @Transactional
        public void createShuushi(ShuushiRegisterDto dto) {
                try {
                        // 収支の登録日時を登録するために現在日時を取得
                        LocalDateTime now = LocalDateTime.now();
                        // 収支テーブルの収支Noを登録するために採番テーブルの値を取得して変数に格納
                        int registerShuushiNo = Integer
                                        .parseInt(saibanService.issueNextNo(CommonConst.SHUUSHI_TABLE_NAME));

                        // 収支エンティティを用いてユーザーの入力値を登録
                        // （コース・レース番号・券種に関してはユーザーが登録しない可能性を考慮してOptional.ofNullable.orElseを使用）
                        Shuushi shuushi = new Shuushi(
                                        registerShuushiNo,
                                        currentUserProvider.getLoginUserNo(),
                                        dto.getRaceDate(),
                                        Optional.ofNullable(dto.getCourseNo()).orElse(0),
                                        Optional.ofNullable(dto.getRaceNo()).orElse(0),
                                        Optional.ofNullable(dto.getKenshuNo()).orElse(0),
                                        dto.getKounyuuKingaku(),
                                        dto.getHaraimodoshi(),
                                        CommonConst.DEL_FLG_ACTIVE,
                                        now,
                                        now);
                        shuushiRepository.registerShuushi(
                                        shuushi.getShuushiNo(),
                                        shuushi.getUserNo(),
                                        shuushi.getRaceDate(),
                                        shuushi.getCourseNo(),
                                        shuushi.getRaceNo(),
                                        shuushi.getKenshuNo(),
                                        shuushi.getKounyuuKingaku(),
                                        shuushi.getHaraimodoshi(),
                                        shuushi.getDelFlg(),
                                        shuushi.getInsDate(),
                                        shuushi.getUpdDate());

                        // ログ出力
                        logger.info("収支登録成功 userNo={} shuushiNo={}", currentUserProvider.getLoginUserNo(),
                                        shuushi.getShuushiNo());

                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("収支登録失敗", e);
                        throw new RuntimeException("収支登録処理でエラーが発生しました", e);
                }
        }

        /**
         * 収支更新処理
         * 
         * @param dto 収支更新用DTO
         */
        @Transactional
        public void updateShuushi(ShuushiUpdateDto dto) {
                try {
                        // 収支の更新日時を登録するために現在日時を取得
                        LocalDateTime now = LocalDateTime.now();
                        // 収支更新用DTOを用いてユーザーの入力値を登録
                        // （コース・レース番号・券種に関してはユーザーが登録しない可能性を考慮してOptional.ofNullable.orElseを使用）
                        int updated = shuushiRepository.updateShuushi(
                                        dto.getShuushiNo(),
                                        dto.getRaceDate(),
                                        Optional.ofNullable(dto.getCourseNo()).orElse(0),
                                        Optional.ofNullable(dto.getRaceNo()).orElse(0),
                                        Optional.ofNullable(dto.getKenshuNo()).orElse(0),
                                        dto.getKounyuuKingaku(),
                                        dto.getHaraimodoshi(),
                                        now,
                                        currentUserProvider.getLoginUserNo(),
                                        CommonConst.DEL_FLG_ACTIVE);

                        if (updated != CommonConst.SINGLE_ROW_UPDATE_COUNT) {
                                throw new IllegalArgumentException("更新対象の収支が存在しないか、権限がありません");
                        }
                        // ログ出力
                        logger.info("収支更新成功 userNo={} shuushiNo={}", currentUserProvider.getLoginUserNo(),
                                        dto.getShuushiNo());
                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("収支更新失敗", e);
                        throw new RuntimeException("収支更新処理でエラーが発生しました", e);
                }

        }

        /**
         * 収支削除処理
         * 
         * @param shuushiNo 収支No
         */
        @Transactional
        public void deleteShuushi(Integer shuushiNo) {
                try {
                        // 収支の更新日時を登録するために現在日時を取得
                        LocalDateTime now = LocalDateTime.now();
                        // 収支削除処理・ログ出力
                        int deleted = shuushiRepository.deleteShuushi(CommonConst.DEL_FLG_DELETED, now, shuushiNo,
                                        currentUserProvider.getLoginUserNo(), CommonConst.DEL_FLG_ACTIVE);
                        if (deleted != CommonConst.SINGLE_ROW_UPDATE_COUNT) {
                                throw new IllegalArgumentException("削除対象の収支が存在しないか、権限がありません");
                        }
                        logger.info("収支削除成功 userNo={} shuushiNo={}", currentUserProvider.getLoginUserNo(), shuushiNo);
                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("収支削除失敗", e);
                        throw new RuntimeException("収支削除処理でエラーが発生しました", e);
                }
        }

}
