package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import com.keibaplus.webap.entity.Saiban;

/**
 * 採番テーブル用リポジトリ
 */
public interface SaibanRepository extends ListCrudRepository<Saiban, String> {

    /**
     * 現在の採番値を取得するためテーブル名で検索する
     * 
     * @param tableName テーブル名
     * @return 採番テーブル取得結果
     */
    @Query("SELECT * FROM SAIBAN WHERE TABLE_NAME = :tableName FOR UPDATE")
    Optional<Saiban> findByTableName(@Param("tableName") String tableName);

    /**
     * 次のデータを登録できるようにするために採番値を更新する
     * 
     * @param saibanNo  採番値
     * @param tableName テーブル名
     */
    @Modifying
    @Query("UPDATE SAIBAN SET SAIBAN_NO = :saibanNo WHERE TABLE_NAME = :tableName")
    void updateSaibanNo(@Param("saibanNo") String saibanNo, @Param("tableName") String tableName);
}
