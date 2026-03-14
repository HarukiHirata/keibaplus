package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import com.keibaplus.webap.entity.Shuushi;

public interface ShuushiRepository extends ListCrudRepository<Shuushi, Integer> {
    @Query("SELECT * FROM SHUUSHI WHERE SHUUSHI = :userNo")
    Optional<Shuushi> findByUserNo(@Param("userNo") String userNo);

    @Modifying
    @Query("""
                INSERT INTO SHUUSHI
                (SHUUSHI_NO, USER_NO, KENSHU_NO, RACE_DATE, COURSE_NO, RACE_NO, KOUNYUU_KINGAKU, HARAIMODOSHI, INS_DATE, UPD_DATE)
                VALUES
                (:shuushiNo, :userNo, :kenshuNo, :raceDate, :courseNo, :raceNo, :kounyuuKingaku, :haraimodoshi, :insDate, :updDate)
            """)
    void registerShuushi(@Param("shuushiNo") int shuushiNo,
            @Param("userNo") String userNo,
            @Param("kenshuNo") String kenshuNo,
            @Param("raceDate") String raceDate,
            @Param("courseNo") String courseNo,
            @Param("raceNo") String raceNo,
            @Param("kounyuuKingaku") int kounyuuKingaku,
            @Param("haraimodoshi") int haraimodoshi,
            @Param("insDate") LocalDateTime insDate,
            @Param("updDate") LocalDateTime updDate);

}
