package com.keibaplus.webap.service;

import java.util.List;

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

@Service
public class ShuushiService {
        private final ShuushiKenshuCourseRepository shuushiKenshuCourseRepository;
        private final ShuushiRepository shuushiRepository;
        private final SaibanRepository saibanRepository;
        private final KenshuRepository kenshuRepository;
        private final CourseRepository courseRepository;
        private final ShuushiSummaryRepository shuushiSummaryRepository;
        private static final Logger logger = LoggerFactory.getLogger(ShuushiService.class);

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

        @Transactional
        public void createShuushi(ShuushiRegisterDto dto) {
                try {
                        LocalDateTime now = LocalDateTime.now();
                        Saiban saiban = saibanRepository.findByTableName("SHUUSHI")
                                        .orElseThrow(() -> new IllegalArgumentException("採番テーブルの値が存在しません"));
                        int newShuushiNo = Integer.parseInt(saiban.getSaibanNo());
                        Shuushi shuushi = new Shuushi(
                                        newShuushiNo,
                                        dto.getUserNo(),
                                        dto.getRaceDate(),
                                        dto.getCourseNo(),
                                        dto.getRaceNo(),
                                        dto.getKenshuNo(),
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

                        String newSaibanNo = Integer.toString(newShuushiNo + 1);

                        saibanRepository.updateSaibanNo(newSaibanNo, "SHUUSHI");

                        logger.info("収支登録成功 userNo={} shuushiNo={}", getLoginUserNo(), shuushi.getShuushiNo());

                } catch (Exception e) {
                        logger.error("収支登録失敗", e);
                }
        }

        public List<Kenshu> findAllKenshu() {
                return kenshuRepository.findAll();
        }

        public List<Course> findAllCourse() {
                return courseRepository.findAll();
        }

        public String getLoginUserNo() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                LoginUser loginUser = (LoginUser) auth.getPrincipal();
                return loginUser.getUserNo();
        }

        public List<ShuushiKenshuCourseDto> findAllShushiByLoginUser(ShuushiSearchDto dto) {
                return shuushiSummaryRepository.findByUserNo(dto);
        }

        public ShuushiUpdateDto getShuushiByShuushiNo(Integer shuushiNo) {
                Shuushi shuushi = shuushiRepository.findByShuushiNo(shuushiNo, "0")
                                .orElseThrow(() -> new IllegalArgumentException("収支テーブルの値が存在しません"));
                ShuushiUpdateDto dto = new ShuushiUpdateDto();
                dto.setShuushiNo(shuushi.getShuushiNo());
                dto.setUserNo(shuushi.getUserNo());
                dto.setKenshuNo(shuushi.getKenshuNo());
                dto.setRaceDate(shuushi.getRaceDate());
                dto.setCourseNo(shuushi.getCourseNo());
                dto.setRaceNo(shuushi.getRaceNo());
                dto.setKounyuuKingaku(shuushi.getKounyuuKingaku());
                dto.setHaraimodoshi(shuushi.getHaraimodoshi());
                return dto;
        }

        @Transactional
        public void updateShuushi(ShuushiUpdateDto dto) {
                try {
                        LocalDateTime now = LocalDateTime.now();
                        shuushiRepository.updateShuushi(
                                        dto.getShuushiNo(),
                                        dto.getRaceDate(),
                                        dto.getCourseNo(),
                                        dto.getRaceNo(),
                                        dto.getKenshuNo(),
                                        dto.getKounyuuKingaku(),
                                        dto.getHaraimodoshi(),
                                        now);
                        logger.info("収支更新成功 userNo={} shuushiNo={}", getLoginUserNo(), dto.getShuushiNo());
                } catch (Exception e) {
                        logger.error("収支更新失敗", e);
                }

        }

        public ShuushiKenshuCourseDto getShuushiByShuushiNoForDelete(Integer shuushiNo) {
                return shuushiKenshuCourseRepository.findByShuushiNo(shuushiNo)
                                .orElseThrow(() -> new IllegalArgumentException("収支テーブルの値が存在しません"));
        }

        @Transactional
        public void deleteShuushi(Integer shuushiNo) {
                try {
                        shuushiRepository.deleteShuushi("1", shuushiNo);
                        logger.info("収支削除成功 userNo={} shuushiNo={}", getLoginUserNo(), shuushiNo);
                } catch (Exception e) {
                        logger.error("収支削除失敗", e);

                }
        }

}
