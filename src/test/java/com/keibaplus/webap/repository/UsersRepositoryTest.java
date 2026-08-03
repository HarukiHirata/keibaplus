package com.keibaplus.webap.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.entity.Users;

@DataJdbcTest
@ActiveProfiles("test")
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("""
                INSERT INTO USERS
                    (USER_NO, USER_ID, PASSWORD, MAIL_ADDRESS, DEL_FLG, INS_DATE, UPD_DATE)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """,
                "U00000001", "existing01", "encoded-password", "existing@example.com",
                CommonConst.DEL_FLG_ACTIVE, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void findByUserNoReturnsOnlyMatchingDeletionStatus() {
        Users user = usersRepository.findByUserNo("U00000001", CommonConst.DEL_FLG_ACTIVE)
                .orElseThrow();

        assertEquals("existing01", user.getUserId());
        assertTrue(usersRepository.findByUserNo("U00000001", CommonConst.DEL_FLG_DELETED).isEmpty());
    }

    @Test
    void duplicateChecksCanExcludeTheCurrentUser() {
        assertTrue(usersRepository.existsByUserId("existing01", CommonConst.DEL_FLG_ACTIVE));
        assertFalse(usersRepository.existsByUserIdAndUserNo(
                "existing01", CommonConst.DEL_FLG_ACTIVE, "U00000001"));
        assertTrue(usersRepository.existsByUserIdAndUserNo(
                "existing01", CommonConst.DEL_FLG_ACTIVE, "U99999999"));
    }

    @Test
    void deleteUserUpdatesDeletionFlag() {
        usersRepository.deleteUser(
                "U00000001", CommonConst.DEL_FLG_DELETED, LocalDateTime.now());

        String deletionFlag = jdbcTemplate.queryForObject(
                "SELECT DEL_FLG FROM USERS WHERE USER_NO = ?", String.class, "U00000001");
        assertEquals(CommonConst.DEL_FLG_DELETED, deletionFlag);
    }
}
