package com.keibaplus.webap.repository;

import org.springframework.stereotype.Repository;

import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.dto.ShuushiSummaryDto;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;

import lombok.RequiredArgsConstructor;

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
                FROM SHUUSHI
                WHERE USER_NO = :userNo
                AND DEL_FLG = :delFlg
                """);

        // sqlのパラメータを設定するためにMapSqlParameterSourceのインスタンスを使用
        MapSqlParameterSource params = new MapSqlParameterSource();
        // ユーザー番号と削除フラグをパラメータに設定
        params.addValue("userNo", dto.getUserNo());
        params.addValue("delFlg", dto.getDelFlg());

        // 開始日をSQLとパラメータに設定
        if (dto.getRaceDateFrom() != null && !dto.getRaceDateFrom().isBlank()) {
            sql.append(" AND RACE_DATE >= :raceDateFrom");
            params.addValue("raceDateFrom", dto.getRaceDateFrom());
        }

        // 終了日をSQLとパラメータに設定
        if (dto.getRaceDateTo() != null && !dto.getRaceDateTo().isBlank()) {
            sql.append(" AND RACE_DATE <= :raceDateTo");
            params.addValue("raceDateTo", dto.getRaceDateTo());
        }

        // 券種をSQLとパラメータに設定
        if (dto.getKenshuNo() != null) {
            sql.append(" AND KENSHU_NO = :kenshuNo");
            params.addValue("kenshuNo", dto.getKenshuNo());
        }

        // コースをSQLとパラメータに設定
        if (dto.getCourseNo() != null) {
            sql.append(" AND COURSE_NO = :courseNo");
            params.addValue("courseNo", dto.getCourseNo());
        }

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
    public List<ShuushiKenshuCourseDto> findByUserNo(ShuushiSearchDto dto) {
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

        // sqlのパラメータを設定するためにMapSqlParameterSourceのインスタンスを使用
        MapSqlParameterSource params = new MapSqlParameterSource();
        // ユーザー番号と削除フラグをパラメータに設定
        params.addValue("userNo", dto.getUserNo());
        params.addValue("delFlg", dto.getDelFlg());

        if (dto.getRaceDateFrom() != null && !dto.getRaceDateFrom().isBlank()) {
            sql.append(" AND s.race_date >= :raceDateFrom");
            params.addValue("raceDateFrom", dto.getRaceDateFrom());
        }

        if (dto.getRaceDateTo() != null && !dto.getRaceDateTo().isBlank()) {
            sql.append(" AND s.race_date <= :raceDateTo");
            params.addValue("raceDateTo", dto.getRaceDateTo());
        }

        if (dto.getKenshuNo() != null) {
            sql.append(" AND s.kenshu_no = :kenshuNo");
            params.addValue("kenshuNo", dto.getKenshuNo());
        }

        if (dto.getCourseNo() != null) {
            sql.append(" AND s.course_no = :courseNo");
            params.addValue("courseNo", dto.getCourseNo());
        }

        // 収支一覧画面で表示する際に収支Noの順で表示するためにソート
        sql.append(" ORDER BY s.shuushi_no");

        // 上記で組み立てたSQLによって取得したデータをreturn（複数行の可能性があるのでqueryを使用）
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
