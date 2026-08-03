package com.keibaplus.webap.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.entity.Saiban;
import com.keibaplus.webap.repository.SaibanRepository;
import com.keibaplus.webap.repository.UsersRepository;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private SaibanRepository saibanRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CustomUserDetailsService customUserDetailsService;

    private UsersService usersService;

    @BeforeEach
    void setUp() {
        usersService = new UsersService(
                usersRepository, saibanRepository, passwordEncoder, customUserDetailsService);
    }

    @Test
    void createUserRegistersEncodedUserAndAdvancesSequence() {
        UsersRegisterDto dto = new UsersRegisterDto();
        dto.setUserId("sample01");
        dto.setMailAddress("sample@example.com");
        dto.setPassword("plainPassword");

        when(saibanRepository.findByTableName(CommonConst.USERS_TABLE_NAME))
                .thenReturn(Optional.of(new Saiban(CommonConst.USERS_TABLE_NAME, "U", "00000001")));
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        usersService.createUser(dto);

        verify(usersRepository).registerUser(
                eq("U00000001"), eq("sample01"), eq("encodedPassword"),
                eq("sample@example.com"), eq(CommonConst.DEL_FLG_ACTIVE),
                any(LocalDateTime.class), any(LocalDateTime.class));
        verify(saibanRepository).updateSaibanNo("00000002", CommonConst.USERS_TABLE_NAME);
    }

    @Test
    void existsByUserIdUsesOnlyActiveUsers() {
        when(usersRepository.existsByUserId("existing01", CommonConst.DEL_FLG_ACTIVE)).thenReturn(true);
        when(usersRepository.existsByUserId("newuser01", CommonConst.DEL_FLG_ACTIVE)).thenReturn(false);

        assertTrue(usersService.existsByUserId("existing01"));
        assertFalse(usersService.existsByUserId("newuser01"));
    }

    @Test
    void deleteUserPerformsLogicalDeletion() {
        usersService.deleteUser("U00000001");

        verify(usersRepository).deleteUser(
                eq("U00000001"), eq(CommonConst.DEL_FLG_DELETED), any(LocalDateTime.class));
    }
}
