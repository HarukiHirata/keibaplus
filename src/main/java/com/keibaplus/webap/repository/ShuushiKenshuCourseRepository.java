package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jdbc.repository.query.Query;

import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;

/**
 * 収支・コース・券種テーブル結合用リポジトリ
 */
public interface ShuushiKenshuCourseRepository extends ListCrudRepository<ShuushiKenshuCourseDto, Integer> {
        /**
         * 収支削除画面で表示するために収支・コース・券種テーブルを結合して取得
         * 
         * @param shuushiNo 収支No
         * @return 収支データ取得結果
         */
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
