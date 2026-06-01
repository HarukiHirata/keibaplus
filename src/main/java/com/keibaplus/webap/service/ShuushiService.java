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
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

/**
 * 収支テーブル関連のService
 */
@Service
public class ShuushiService {
        // 必要なrepositoryのインスタンスを使用
        private final ShuushiKenshuCourseRepository shuushiKenshuCourseRepository;
        private final ShuushiRepository shuushiRepository;
        private final SaibanRepository saibanRepository;
        private final KenshuRepository kenshuRepository;
        private final CourseRepository courseRepository;
        private final ShuushiSummaryRepository shuushiSummaryRepository;
        // ロガーの定義
        private static final Logger logger = LoggerFactory.getLogger(ShuushiService.class);

        // コンストラクタ
        public ShuushiService(ShuushiRepository shuushiRepository, SaibanRepository saibanRepository,
                        KenshuRepository kenshuRepository, CourseRepository courseRepository,
                        ShuushiSummaryRepository shuushiSummaryRepository,
                        ShuushiKenshuCourseRepository shuushiKenshuCourseRepository) {
                this.shuushiRepository = shuushiRepository;
                this.saibanRepository = saibanRepository;
                this.kenshuRepository = kenshuRepository;
                this.courseRepository = courseRepository;
                this.shuushiSummaryRepository = shuushiSummaryRepository;
                this.shuushiKenshuCourseRepository = shuushiKenshuCourseRepository;
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
                        Saiban saiban = saibanRepository.findByTableName("SHUUSHI")
                                        .orElseThrow(() -> new IllegalArgumentException("採番テーブルの値が存在しません"));
                        int newShuushiNo = Integer.parseInt(saiban.getSaibanNo());

                        // 収支エンティティを用いてユーザーの入力値を登録
                        // （コース・レース番号・券種に関してはユーザーが登録しない可能性を考慮してOptional.ofNullable.orElseを使用）
                        Shuushi shuushi = new Shuushi(
                                        newShuushiNo,
                                        getLoginUserNo(),
                                        dto.getRaceDate(),
                                        Optional.ofNullable(dto.getCourseNo()).orElse(0),
                                        Optional.ofNullable(dto.getRaceNo()).orElse(0),
                                        Optional.ofNullable(dto.getKenshuNo()).orElse(0),
                                        dto.getKounyuuKingaku(),
                                        dto.getHaraimodoshi(),
                                        "0",
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

                        // 次の収支の登録に使用するため採番テーブルの値をインクリメント
                        String newSaibanNo = Integer.toString(newShuushiNo + 1);

                        saibanRepository.updateSaibanNo(newSaibanNo, "SHUUSHI");

                        // ログ出力
                        logger.info("収支登録成功 userNo={} shuushiNo={}", getLoginUserNo(), shuushi.getShuushiNo());

                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("収支登録失敗", e);
                        throw new RuntimeException("収支登録処理でエラーが発生しました", e);
                }
        }

        /**
         * 券種テーブル取得処理
         * 
         * @return 券種テーブル取得結果
         */
        public List<Kenshu> findAllKenshu() {
                return kenshuRepository.findAll();
        }

        /**
         * コーステーブル取得処理
         * 
         * @return コーステーブル取得結果
         */
        public List<Course> findAllCourse() {
                return courseRepository.findAll();
        }

        /**
         * ログインユーザー番号取得処理
         * 
         * @return ログインユーザーのユーザー番号
         */
        public String getLoginUserNo() {
                // Spring Securityの認証情報を取得
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                // ユーザー名以外のデータを取得するためにPrincipalを格納してそこからユーザー番号を取得
                LoginUser loginUser = (LoginUser) auth.getPrincipal();
                return loginUser.getUserNo();
        }

        /**
         * 収支取得処理
         * 
         * @param dto 収支表示用DTO
         * @return 収支テーブル取得結果
         */
        public List<ShuushiKenshuCourseDto> findAllShushiByLoginUser(ShuushiSearchDto dto) {
                return shuushiSummaryRepository.findByUserNo(dto);
        }

        /**
         * 収支編集画面で表示するために収支を取得
         * 
         * @param shuushiNo 収支No
         * @return 収支更新用DTO（収支データ格納済み）
         */
        public ShuushiUpdateDto getShuushiByShuushiNo(Integer shuushiNo) {
                // 収支データ取得
                Shuushi shuushi = shuushiRepository.findByShuushiNo(shuushiNo, "0")
                                .orElseThrow(() -> new IllegalArgumentException("収支テーブルの値が存在しません"));
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
                        shuushiRepository.updateShuushi(
                                        dto.getShuushiNo(),
                                        dto.getRaceDate(),
                                        Optional.ofNullable(dto.getCourseNo()).orElse(0),
                                        Optional.ofNullable(dto.getRaceNo()).orElse(0),
                                        Optional.ofNullable(dto.getKenshuNo()).orElse(0),
                                        dto.getKounyuuKingaku(),
                                        dto.getHaraimodoshi(),
                                        now);
                        // ログ出力
                        logger.info("収支更新成功 userNo={} shuushiNo={}", getLoginUserNo(), dto.getShuushiNo());
                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("収支更新失敗", e);
                        throw new RuntimeException("収支更新処理でエラーが発生しました", e);
                }

        }

        /**
         * 収支削除画面で表示するために収支を取得
         * 
         * @param shuushiNo 収支No
         * @return 収支テーブル取得結果
         */
        public ShuushiKenshuCourseDto getShuushiByShuushiNoForDelete(Integer shuushiNo) {
                return shuushiKenshuCourseRepository.findByShuushiNo(shuushiNo)
                                .orElseThrow(() -> new IllegalArgumentException("収支テーブルの値が存在しません"));
        }

        /**
         * 収支削除処理
         * 
         * @param shuushiNo 収支No
         */
        @Transactional
        public void deleteShuushi(Integer shuushiNo) {
                try {
                        // 収支削除処理・ログ出力
                        shuushiRepository.deleteShuushi("1", shuushiNo);
                        logger.info("収支削除成功 userNo={} shuushiNo={}", getLoginUserNo(), shuushiNo);
                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("収支削除失敗", e);
                        throw new RuntimeException("収支削除処理でエラーが発生しました", e);
                }
        }

}
