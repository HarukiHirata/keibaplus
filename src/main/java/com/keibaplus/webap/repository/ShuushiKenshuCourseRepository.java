package com.keibaplus.webap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jdbc.repository.query.Query;

import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;

public interface ShuushiKenshuCourseRepository extends ListCrudRepository<ShuushiKenshuCourseDto, Integer> {
    @Query("""
            SELECT
            SHUUSHI.SHUUSHI_NO,
            SHUUSHI.USER_NO,
            KENSHU.KENSHU_NAME,
            SHUUSHI.RACE_DATE,
            COURSE.COURSE_NAME,
            SHUUSHI.RACE_NO,
            SHUUSHI.KOUNYUU_KINGAKU,
            SHUUSHI.HARAIMODOSHI
            FROM SHUUSHI
            JOIN KENSHU ON SHUUSHI.KENSHU_NO = KENSHU.KENSHU_NO
            JOIN COURSE ON SHUUSHI.COURSE_NO = COURSE.COURSE_NO
            WHERE USER_NO = :userNo
            ORDER BY SHUUSHI_NO
            """)
    List<ShuushiKenshuCourseDto> findByUserNo(@Param("userNo") String userNo);

    @Query("""
            SELECT
            SHUUSHI.SHUUSHI_NO,
            SHUUSHI.USER_NO,
            KENSHU.KENSHU_NAME,
            SHUUSHI.RACE_DATE,
            COURSE.COURSE_NAME,
            SHUUSHI.RACE_NO,
            SHUUSHI.KOUNYUU_KINGAKU,
            SHUUSHI.HARAIMODOSHI
            FROM SHUUSHI
            JOIN KENSHU ON SHUUSHI.KENSHU_NO = KENSHU.KENSHU_NO
            JOIN COURSE ON SHUUSHI.COURSE_NO = COURSE.COURSE_NO
            WHERE SHUUSHI_NO = :shuushiNo
            """)
    Optional<ShuushiKenshuCourseDto> findByShuushiNo(@Param("shuushiNo") Integer shuushiNo);
}
