package com.keibaplus.webap.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import com.keibaplus.webap.entity.Shuushi;

public interface ShuushiRepository extends ListCrudRepository<Shuushi, Integer> {
        @Query("SELECT * FROM SHUUSHI WHERE USER_NO = :userNo AND DEL_FLG = :delFlg ORDER BY SHUUSHI_NO")
        List<Shuushi> findByUserNo(@Param("userNo") String userNo,
                        @Param("delFlg") String delFlg);

        @Query("SELECT * FROM SHUUSHI WHERE SHUUSHI_NO = :shuushiNo AND DEL_FLG = :delFlg")
        Optional<Shuushi> findByShuushiNo(@Param("shuushiNo") Integer shuushiNo,
                        @Param("delFlg") String delFlg);

        @Modifying
        @Query("""
                            INSERT INTO SHUUSHI
                            (SHUUSHI_NO, USER_NO, RACE_DATE, COURSE_NO, RACE_NO, KENSHU_NO, KOUNYUU_KINGAKU, HARAIMODOSHI, DEL_FLG, INS_DATE, UPD_DATE)
                            VALUES
                            (:shuushiNo, :userNo, :raceDate, :courseNo, :raceNo, :kenshuNo, :kounyuuKingaku, :haraimodoshi, :delFlg, :insDate, :updDate)
                        """)
        void registerShuushi(@Param("shuushiNo") int shuushiNo,
                        @Param("userNo") String userNo,
                        @Param("raceDate") String raceDate,
                        @Param("courseNo") int courseNo,
                        @Param("raceNo") int raceNo,
                        @Param("kenshuNo") int kenshuNo,
                        @Param("kounyuuKingaku") int kounyuuKingaku,
                        @Param("haraimodoshi") int haraimodoshi,
                        @Param("delFlg") String delFlg,
                        @Param("insDate") LocalDateTime insDate,
                        @Param("updDate") LocalDateTime updDate);

        @Modifying
        @Query("""
                        UPDATE SHUUSHI
                        SET RACE_DATE = :raceDate,
                        COURSE_NO = :courseNo,
                        RACE_NO = :raceNo,
                        KENSHU_NO = :kenshuNo,
                        KOUNYUU_KINGAKU = :kounyuuKingaku,
                        HARAIMODOSHI = :haraimodoshi,
                        UPD_DATE = :updDate
                        WHERE SHUUSHI_NO = :shuushiNo
                        """)
        void updateShuushi(@Param("shuushiNo") int shuushiNo,
                        @Param("raceDate") String raceDate,
                        @Param("courseNo") int courseNo,
                        @Param("raceNo") int raceNo,
                        @Param("kenshuNo") int kenshuNo,
                        @Param("kounyuuKingaku") int kounyuuKingaku,
                        @Param("haraimodoshi") int haraimodoshi,
                        @Param("updDate") LocalDateTime updDate);

        @Modifying
        @Query("""
                        UPDATE SHUUSHI
                        SET DEL_FLG = :delFlg
                        WHERE SHUUSHI_NO = :shuushiNo
                        """)
        void deleteShuushi(@Param("delFlg") String delFlg,
                        @Param("shuushiNo") int shuushiNo);

}
