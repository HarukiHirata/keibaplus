package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import com.keibaplus.webap.entity.Shuushi;

/**
 * 収支テーブル用リポジトリ
 */
public interface ShuushiRepository extends ListCrudRepository<Shuushi, Integer> {
    /**
     * 収支編集画面でデータを表示するため収支Noで検索する
     * 
     * @param shuushiNo 収支No
     * @param delFlg    削除フラグ
     * @return 収支テーブル取得結果
     */
    @Query("SELECT * FROM SHUUSHI WHERE SHUUSHI_NO = :shuushiNo AND DEL_FLG = :delFlg")
    Optional<Shuushi> findByShuushiNo(@Param("shuushiNo") Integer shuushiNo,
            @Param("delFlg") String delFlg);

    /**
     * 収支登録
     * 
     * @param shuushiNo      収支No
     * @param userNo         ユーザー番号
     * @param raceDate       レース日
     * @param courseNo       コースNo
     * @param raceNo         レース番号
     * @param kenshuNo       券種No
     * @param kounyuuKingaku 購入金額
     * @param haraimodoshi   払い戻し
     * @param delFlg         削除フラグ
     * @param insDate        登録日時
     * @param updDate        更新日時
     */
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

    /**
     * 収支更新
     * 
     * @param shuushiNo      収支No
     * @param raceDate       レース日
     * @param courseNo       コースNo
     * @param raceNo         レース番号
     * @param kenshuNo       券種No
     * @param kounyuuKingaku 購入金額
     * @param haraimodoshi   払い戻し
     * @param updDate        更新日時
     */
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
            AND USER_NO = :userNo
            AND DEL_FLG = :delFlg
            """)
    void updateShuushi(@Param("shuushiNo") int shuushiNo,
            @Param("raceDate") String raceDate,
            @Param("courseNo") int courseNo,
            @Param("raceNo") int raceNo,
            @Param("kenshuNo") int kenshuNo,
            @Param("kounyuuKingaku") int kounyuuKingaku,
            @Param("haraimodoshi") int haraimodoshi,
            @Param("updDate") LocalDateTime updDate,
            @Param("userNo") String userNo,
            @Param("delFlg") String delFlg);

    /**
     * 収支削除
     * 
     * @param delFlg    削除フラグ
     * @param shuushiNo 収支No
     */
    @Modifying
    @Query("""
            UPDATE SHUUSHI
            SET DEL_FLG = :delFlg
            WHERE SHUUSHI_NO = :shuushiNo
            AND USER_NO = :userNo
            """)
    void deleteShuushi(@Param("delFlg") String delFlg,
            @Param("shuushiNo") int shuushiNo,
            @Param("userNo") String userNo);

}
