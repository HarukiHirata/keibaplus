package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.keibaplus.webap.entity.Users;

public interface UsersRepository extends ListCrudRepository<Users, String> {

    @Query("SELECT * FROM USERS WHERE USER_NO = :userNo")
    Optional<Users> findByUserNo(@Param("userNo") String userNo);

    @Query("SELECT * FROM USERS WHERE USER_ID = :userId")
    Optional<Users> findByUserId(@Param("userId") String userId);

    @Query("SELECT * FROM USERS WHERE MAILADDRESS = :mailAddress")
    Optional<Users> findByMailAddress(@Param("mailAddress") String mailAddress);

}
