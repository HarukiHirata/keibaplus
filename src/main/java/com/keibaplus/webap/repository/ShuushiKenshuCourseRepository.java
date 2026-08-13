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
         * @param userNo    ユーザー番号
         * @param delFlg    削除フラグ
         * @return 収支データ取得結果
         */
        @Query("""
                        SELECT
                        s.shuushi_no,
                        s.user_no,
                        k.kenshu_name,
                        s.race_date,
                        c.course_name,
                        s.race_no,
                        s.kounyuu_kingaku,
                        s.haraimodoshi
                        FROM shuushi s
                        JOIN kenshu k ON s.kenshu_no = k.kenshu_no
                        JOIN course c ON s.course_no = c.course_no
                        WHERE s.shuushi_no = :shuushiNo
                        AND s.user_no = :userNo
                        AND s.del_flg = :delFlg
                        """)
        Optional<ShuushiKenshuCourseDto> findByShuushiNo(@Param("shuushiNo") Integer shuushiNo,
                        @Param("userNo") String userNo,
                        @Param("delFlg") String delFlg);
}
