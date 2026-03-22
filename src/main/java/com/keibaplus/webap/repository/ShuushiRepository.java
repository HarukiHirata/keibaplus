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
    @Query("SELECT * FROM SHUUSHI WHERE USER_NO = :userNo ORDER BY SHUUSHI_NO")
    List<Shuushi> findByUserNo(@Param("userNo") String userNo);

    @Query("SELECT * FROM SHUUSHI WHERE SHUUSHI_NO = :shuushiNo")
    Optional<Shuushi> findByShuushiNo(@Param("shuushiNo") Integer shuushiNo);

    @Modifying
    @Query("""
                INSERT INTO SHUUSHI
                (SHUUSHI_NO, USER_NO, KENSHU_NO, RACE_DATE, COURSE_NO, RACE_NO, KOUNYUU_KINGAKU, HARAIMODOSHI, INS_DATE, UPD_DATE)
                VALUES
                (:shuushiNo, :userNo, :kenshuNo, :raceDate, :courseNo, :raceNo, :kounyuuKingaku, :haraimodoshi, :insDate, :updDate)
            """)
    void registerShuushi(@Param("shuushiNo") int shuushiNo,
            @Param("userNo") String userNo,
            @Param("kenshuNo") int kenshuNo,
            @Param("raceDate") String raceDate,
            @Param("courseNo") int courseNo,
            @Param("raceNo") int raceNo,
            @Param("kounyuuKingaku") int kounyuuKingaku,
            @Param("haraimodoshi") int haraimodoshi,
            @Param("insDate") LocalDateTime insDate,
            @Param("updDate") LocalDateTime updDate);

    @Modifying
    @Query("""
            UPDATE SHUUSHI
            SET KENSHU_NO = :kenshuNo,
            RACE_DATE = :raceDate,
            COURSE_NO = :courseNo,
            RACE_NO = :raceNo,
            KOUNYUU_KINGAKU = :kounyuuKingaku,
            HARAIMODOSHI = :haraimodoshi,
            UPD_DATE = :updDate
            WHERE SHUUSHI_NO = :shuushiNo
            """)
    void updateShuushi(@Param("shuushiNo") int shuushiNo,
            @Param("kenshuNo") int kenshuNo,
            @Param("raceDate") String raceDate,
            @Param("courseNo") int courseNo,
            @Param("raceNo") int raceNo,
            @Param("kounyuuKingaku") int kounyuuKingaku,
            @Param("haraimodoshi") int haraimodoshi,
            @Param("updDate") LocalDateTime updDate);

}
