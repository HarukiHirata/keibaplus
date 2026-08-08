package com.keibaplus.webap.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.dto.ShuushiSummaryDto;
import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 収支テーブル検索用リポジトリ
 */
@Repository
@RequiredArgsConstructor
public class ShuushiSummaryRepository {
    // 検索条件が複数ありユーザーの入力により変わるためNamedParameterJdbcTemplateのインスタンスを使用
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * ユーザーが入力した条件に当てはまる回収率を表示するため、条件による購入金額と払い戻しの合計を取得
     * 
     * @param dto 収支検索用DTO
     * @return 購入金額・払い戻し合計
     */
    public ShuushiSummaryDto searchSummary(ShuushiSearchDto dto) {
        // sqlを組み立てるためStringBuilderのインスタンスを使用
        StringBuilder sql = new StringBuilder();

        // どの条件にも当てはまるようなsql
        sql.append("""
                SELECT
                COALESCE(SUM(KOUNYUU_KINGAKU),0) AS "totalKounyuuKingaku",
                COALESCE(SUM(HARAIMODOSHI),0) AS "totalHaraimodoshi"
                FROM SHUUSHI s
                WHERE s.USER_NO = :userNo
                AND s.DEL_FLG = :delFlg
                """);

        sql.append(appendQuery(dto));

        // sqlのパラメータを設定するためにMapSqlParameterSourceのインスタンスを使用
        MapSqlParameterSource params = getSqlParameter(dto);

        // 上記で組み立てたSQLによって取得したデータをreturn（単一業の想定なのでqueryForObjectを使用）
        return namedParameterJdbcTemplate.queryForObject(sql.toString(), params, (rs,
                rowNum) -> {
            int totalKounyuuKingaku = rs.getInt("totalKounyuuKingaku");
            int totalHaraimodoshi = rs.getInt("totalHaraimodoshi");
            return new ShuushiSummaryDto(
                    totalKounyuuKingaku,
                    totalHaraimodoshi);
        });
    }

    /**
     * ユーザーが入力した条件に当てはまる収支を表示するため、条件による収支のデータを取得
     * 
     * @param dto 収支検索用DTO
     * @return 収支データ取得結果
     */
    public List<ShuushiKenshuCourseDto> findByUserNo(ShuushiSearchDto dto, int limit, long offset) {
        // sqlを組み立てるためStringBuilderのインスタンスを使用
        StringBuilder sql = new StringBuilder();
        // どの条件にも当てはまるようなsql
        sql.append(
                """
                        SELECT
                        s.shuushi_no AS "shuushiNo",
                        s.user_no AS "userNo",
                        s.race_date AS "raceDate",
                        c.course_name AS "courseName",
                        s.race_no AS "raceNo",
                        k.kenshu_name AS "kenshuName",
                        s.kounyuu_kingaku AS "kounyuuKingaku",
                        s.haraimodoshi AS "haraimodoshi"
                        FROM shuushi s
                        LEFT JOIN kenshu k ON s.kenshu_no = k.kenshu_no
                        LEFT JOIN course c ON s.course_no = c.course_no
                        WHERE s.user_no = :userNo
                        AND s.del_flg = :delFlg
                        """);

        sql.append(appendQuery(dto));

        // 収支一覧画面で表示する際に収支Noの順で表示するためにソート
        sql.append("""
                ORDER BY s.shuushi_no
                LIMIT :limit
                OFFSET :offset
                """);

        // sqlのパラメータを設定するためにMapSqlParameterSourceのインスタンスを使用
        MapSqlParameterSource params = getSqlParameter(dto);

        params.addValue("limit", limit);
        params.addValue("offset", offset);

        // 上記で組み立てたSQLによって取得したデータをreturn（複数行の可能性があるのでqueryを使用）
        return namedParameterJdbcTemplate.query(sql.toString(), params, (rs,
                rowNum) -> {
            return new ShuushiKenshuCourseDto(
                    rs.getInt("shuushiNo"),
                    rs.getString("userNo"),
                    rs.getObject("raceDate", LocalDate.class),
                    rs.getString("courseName"),
                    rs.getInt("raceNo"),
                    rs.getString("kenshuName"),
                    rs.getInt("kounyuuKingaku"),
                    rs.getInt("haraimodoshi"));
        });

    }

    public long countByUserNo(ShuushiSearchDto dto) {
        StringBuilder sql = new StringBuilder();
        sql.append("""
                SELECT COUNT(*)
                FROM shuushi s
                WHERE s.user_no = :userNo
                AND s.del_flg = :delFlg
                """);
        sql.append(appendQuery(dto));
        MapSqlParameterSource params = getSqlParameter(dto);

        Long count = namedParameterJdbcTemplate.queryForObject(
                sql.toString(),
                params,
                Long.class);

        return count != null ? count : 0L;

    }

    private StringBuilder appendQuery(ShuushiSearchDto dto) {
        StringBuilder sql = new StringBuilder();
        // 開始日をSQLとパラメータに設定
        if (dto.getRaceDateFrom() != null) {
            sql.append(" AND s.RACE_DATE >= :raceDateFrom");
            sql.append(CommonConst.HANKAKU_SPACE);
        }

        // 終了日をSQLとパラメータに設定
        if (dto.getRaceDateTo() != null) {
            sql.append(" AND s.RACE_DATE <= :raceDateTo");
            sql.append(CommonConst.HANKAKU_SPACE);
        }

        // 券種をSQLとパラメータに設定
        if (dto.getKenshuNo() != null) {
            sql.append(" AND s.KENSHU_NO = :kenshuNo");
            sql.append(CommonConst.HANKAKU_SPACE);
        }

        // コースをSQLとパラメータに設定
        if (dto.getCourseNo() != null) {
            sql.append(" AND s.COURSE_NO = :courseNo");
            sql.append(CommonConst.HANKAKU_SPACE);
        }

        return sql;

    }

    private MapSqlParameterSource getSqlParameter(ShuushiSearchDto dto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userNo", dto.getUserNo());
        params.addValue("delFlg", dto.getDelFlg());

        if (dto.getRaceDateFrom() != null) {
            params.addValue("raceDateFrom", dto.getRaceDateFrom());
        }

        if (dto.getRaceDateTo() != null) {
            params.addValue("raceDateTo", dto.getRaceDateTo());
        }

        if (dto.getKenshuNo() != null) {
            params.addValue("kenshuNo", dto.getKenshuNo());
        }

        if (dto.getCourseNo() != null) {
            params.addValue("courseNo", dto.getCourseNo());
        }

        return params;
    }

}
