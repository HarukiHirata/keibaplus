package com.keibaplus.webap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keibaplus.webap.entity.Users;
import com.keibaplus.webap.entity.Saiban;
import com.keibaplus.webap.entity.Shuushi;
import com.keibaplus.webap.entity.Kenshu;
import com.keibaplus.webap.entity.Course;
import com.keibaplus.webap.repository.ShuushiRepository;
import com.keibaplus.webap.repository.UsersRepository;
import com.keibaplus.webap.repository.KenshuRepository;
import com.keibaplus.webap.repository.CourseRepository;
import com.keibaplus.webap.repository.SaibanRepository;
import com.keibaplus.webap.dto.ShuushiRegisterDto;
import com.keibaplus.webap.dto.ShuushiResponseDto;
import com.keibaplus.webap.dto.UsersResponseDto;

import java.time.LocalDateTime;

@Service
public class ShuushiService {
        private final ShuushiRepository shuushiRepository;
        private final SaibanRepository saibanRepository;
        private final KenshuRepository kenshuRepository;
        private final CourseRepository courseRepository;

        public ShuushiService(ShuushiRepository shuushiRepository, SaibanRepository saibanRepository,
                        KenshuRepository kenshuRepository, CourseRepository courseRepository) {
                this.shuushiRepository = shuushiRepository;
                this.saibanRepository = saibanRepository;
                this.kenshuRepository = kenshuRepository;
                this.courseRepository = courseRepository;
        }

        @Transactional
        public ShuushiResponseDto createShuushi(ShuushiRegisterDto dto) {
                LocalDateTime now = LocalDateTime.now();
                Saiban saiban = saibanRepository.findByTableName("SHUUSHI")
                                .orElseThrow(() -> new IllegalArgumentException("採番テーブルの値が存在しません"));
                int newShuushiNo = Integer.parseInt(saiban.getSaibanNo());
                Shuushi shuushi = new Shuushi(
                                newShuushiNo,
                                dto.getUserNo(),
                                dto.getKenshuNo(),
                                dto.getRaceDate(),
                                dto.getCourseNo(),
                                dto.getRaceNo(),
                                dto.getKounyuuKingaku(),
                                dto.getHaraimodoshi(),
                                now,
                                now);
                shuushiRepository.registerShuushi(
                                shuushi.getShuushiNo(),
                                shuushi.getUserNo(),
                                shuushi.getKenshuNo(),
                                shuushi.getRaceDate(),
                                shuushi.getCourseNo(),
                                shuushi.getRaceNo(),
                                shuushi.getKounyuuKingaku(),
                                shuushi.getHaraimodoshi(),
                                shuushi.getInsDate(),
                                shuushi.getUpdDate());

                String newSaibanNo = Integer.toString(newShuushiNo + 1);

                saibanRepository.updateSaibanNo(newSaibanNo, "SHUUSHI");

                return new ShuushiResponseDto(
                                shuushi.getUserNo(),
                                shuushi.getKenshuNo(),
                                shuushi.getRaceDate(),
                                shuushi.getCourseNo(),
                                shuushi.getRaceNo(),
                                shuushi.getKounyuuKingaku(),
                                shuushi.getHaraimodoshi());
        }

        public List<Kenshu> findAllKenshu() {
                return kenshuRepository.findAll();
        }

        public List<Course> findAllCourse() {
                return courseRepository.findAll();
        }

}
