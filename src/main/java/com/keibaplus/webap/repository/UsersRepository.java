package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import com.keibaplus.webap.entity.Users;

public interface UsersRepository extends ListCrudRepository<Users, String> {

        @Query("SELECT * FROM USERS WHERE USER_NO = :userNo")
        Optional<Users> findByUserNo(@Param("userNo") String userNo);

        @Query("SELECT * FROM USERS WHERE USER_ID = :userId")
        Optional<Users> findByUserId(@Param("userId") String userId);

        @Query("SELECT * FROM USERS WHERE MAILADDRESS = :mailAddress")
        Optional<Users> findByMailAddress(@Param("mailAddress") String mailAddress);

        @Query("""
                        SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
                        FROM USERS
                        WHERE USER_ID = :userId
                        AND DEL_FLG = 0
                        """)
        boolean existsByUserId(String userId);

        @Query("""
                        SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
                        FROM USERS
                        WHERE MAIL_ADDRESS = :mailAddress
                        AND DEL_FLG = 0
                        """)

        boolean existsByMailAddress(String mailAddress);

        @Query("""
                        SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
                        FROM USERS
                        WHERE USER_ID = :userId
                        AND DEL_FLG = 0
                        AND USER_NO != :userNo
                        """)
        boolean existsByUserIdAndUserNo(String userId, String userNo);

        @Query("""
                        SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
                        FROM USERS
                        WHERE MAIL_ADDRESS = :mailAddress
                        AND DEL_FLG = 0
                        AND USER_NO != :userNo
                        """)

        boolean existsByMailAddressAndUserNo(String mailAddress, String userNo);

        @Modifying
        @Query("""
                            INSERT INTO USERS
                            (USER_NO, USER_ID, PASSWORD, MAIL_ADDRESS, DEL_FLG, LAST_LOGIN_DATE, INS_DATE, UPD_DATE)
                            VALUES
                            (:userNo, :userId, :password, :mailAddress, :delFlg, :lastLoginDate, :insDate, :updDate)
                        """)
        void registerUser(@Param("userNo") String userNo,
                        @Param("userId") String userId,
                        @Param("password") String password,
                        @Param("mailAddress") String mailAddress,
                        @Param("delFlg") String delFlg,
                        @Param("lastLoginDate") LocalDateTime lastLoginDate,
                        @Param("insDate") LocalDateTime insDate,
                        @Param("updDate") LocalDateTime updDate);

        @Modifying
        @Query("""
                        UPDATE USERS
                        SET USER_ID = :userId,
                        MAIL_ADDRESS = :mailAddress
                        WHERE USER_NO = :userNo
                        """)
        void updateUser(@Param("userNo") String userNo,
                        @Param("userId") String userId,
                        @Param("mailAddress") String mailAddress);

        @Modifying
        @Query("""
                        UPDATE USERS
                        SET PASSWORD = :password
                        WHERE USER_NO = :userNo
                        """)
        void updatePassword(@Param("userNo") String userNo,
                        @Param("password") String password);
}
