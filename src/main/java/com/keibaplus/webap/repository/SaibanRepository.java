package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import com.keibaplus.webap.entity.Saiban;

public interface SaibanRepository extends ListCrudRepository<Saiban, String> {

    @Query("SELECT * FROM SAIBAN WHERE TABLE_NAME = :tableName")
    Optional<Saiban> findByTableName(@Param("tableName") String tableName);

    @Modifying
    @Query("UPDATE SAIBAN SET SAIBAN_NO = :saibanNo WHERE TABLE_NAME = :tableName")
    void updateSaibanNo(@Param("saibanNo") String saibanNo, @Param("tableName") String tableName);
}
