package com.keibaplus.webap.repository;

import org.springframework.stereotype.Repository;

import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.dto.ShuushiSummaryDto;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Repository
@RequiredArgsConstructor
public class ShuushiSummaryRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ShuushiSummaryDto searchSummary(ShuushiSearchDto dto) {
        StringBuilder sql = new StringBuilder();

        sql.append("""
                SELECT
                COALESCE(SUM(KOUNYUU_KINGAKU),0) AS "totalKounyuuKingaku",
                COALESCE(SUM(HARAIMODOSHI),0) AS "totalHaraimodoshi"
                FROM SHUUSHI
                WHERE USER_NO = :userNo
                AND DEL_FLG = :delFlg
                """);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userNo", dto.getUserNo());
        params.addValue("delFlg", dto.getDelFlg());

        if (dto.getRaceDateFrom() != null && !dto.getRaceDateFrom().isBlank()) {
            sql.append(" AND RACE_DATE >= :raceDateFrom");
            params.addValue("raceDateFrom", dto.getRaceDateFrom());
        }

        if (dto.getRaceDateTo() != null && !dto.getRaceDateTo().isBlank()) {
            sql.append(" AND RACE_DATE <= :raceDateTo");
            params.addValue("raceDateTo", dto.getRaceDateTo());
        }

        if (dto.getKenshuNo() != null) {
            sql.append(" AND KENSHU_NO = :kenshuNo");
            params.addValue("kenshuNo", dto.getKenshuNo());
        }

        if (dto.getCourseNo() != null) {
            sql.append(" AND COURSE_NO = :courseNo");
            params.addValue("courseNo", dto.getCourseNo());
        }

        return namedParameterJdbcTemplate.queryForObject(sql.toString(), params, (rs,
                rowNum) -> {
            int totalKounyuuKingaku = rs.getInt("totalKounyuuKingaku");
            int totalHaraimodoshi = rs.getInt("totalHaraimodoshi");
            return new ShuushiSummaryDto(
                    totalKounyuuKingaku,
                    totalHaraimodoshi);
        });
    }

    public List<ShuushiKenshuCourseDto> findByUserNo(ShuushiSearchDto dto) {
        StringBuilder sql = new StringBuilder();
        sql.append(
                """
                        SELECT
                        SHUUSHI.SHUUSHI_NO AS "shuushiNo",
                        SHUUSHI.USER_NO AS "userNo",
                        SHUUSHI.RACE_DATE AS "raceDate",
                        COURSE.COURSE_NAME AS "courseName",
                        SHUUSHI.RACE_NO AS "raceNo",
                        KENSHU.KENSHU_NAME AS "kenshuName",
                        SHUUSHI.KOUNYUU_KINGAKU AS "kounyuuKingaku",
                        SHUUSHI.HARAIMODOSHI AS "haraimodoshi"
                        FROM SHUUSHI
                        JOIN KENSHU ON SHUUSHI.KENSHU_NO = KENSHU.KENSHU_NO
                        JOIN COURSE ON SHUUSHI.COURSE_NO = COURSE.COURSE_NO
                        WHERE SHUUSHI.USER_NO = :userNo
                        AND DEL_FLG = :delFlg
                        """);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userNo", dto.getUserNo());
        params.addValue("delFlg", dto.getDelFlg());

        if (dto.getRaceDateFrom() != null && !dto.getRaceDateFrom().isBlank()) {
            sql.append(" AND SHUUSHI.RACE_DATE >= :raceDateFrom");
            params.addValue("raceDateFrom", dto.getRaceDateFrom());
        }

        if (dto.getRaceDateTo() != null && !dto.getRaceDateTo().isBlank()) {
            sql.append(" AND SHUUSHI.RACE_DATE <= :raceDateTo");
            params.addValue("raceDateTo", dto.getRaceDateTo());
        }

        if (dto.getKenshuNo() != null) {
            sql.append(" AND SHUUSHI.KENSHU_NO = :kenshuNo");
            params.addValue("kenshuNo", dto.getKenshuNo());
        }

        if (dto.getCourseNo() != null) {
            sql.append(" AND SHUUSHI.COURSE_NO = :courseNo");
            params.addValue("courseNo", dto.getCourseNo());
        }

        sql.append(" ORDER BY SHUUSHI.SHUUSHI_NO");

        return namedParameterJdbcTemplate.query(sql.toString(), params, (rs,
                rowNum) -> {
            return new ShuushiKenshuCourseDto(
                    rs.getInt("shuushiNo"),
                    rs.getString("userNo"),
                    rs.getString("raceDate"),
                    rs.getString("courseName"),
                    rs.getInt("raceNo"),
                    rs.getString("kenshuName"),
                    rs.getInt("kounyuuKingaku"),
                    rs.getInt("haraimodoshi"));
        });

    }

}
