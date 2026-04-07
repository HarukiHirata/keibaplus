package com.keibaplus.webap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keibaplus.webap.dto.UsersResponseDto;
import com.keibaplus.webap.dto.UsersUpdateDto;
import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.entity.Users;
import com.keibaplus.webap.entity.Saiban;
import com.keibaplus.webap.repository.UsersRepository;
import com.keibaplus.webap.repository.SaibanRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

@Service
public class UsersService {
        private final UsersRepository usersRepository;
        private final SaibanRepository saibanRepository;
        private final PasswordEncoder passwordEncoder;

        public UsersService(UsersRepository usersRepository, SaibanRepository saibanRepository,
                        PasswordEncoder passwordEncoder) {
                this.usersRepository = usersRepository;
                this.saibanRepository = saibanRepository;
                this.passwordEncoder = passwordEncoder;
        }

        public List<UsersResponseDto> findAllUsers() {
                return usersRepository.findAll().stream()
                                .map(user -> new UsersResponseDto(
                                                user.getUserNo(),
                                                user.getUserId(),
                                                user.getMailAddress(),
                                                user.getLastLoginDate()))
                                .toList();
        }

        public LoginUser getLoginUser() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                LoginUser loginUser = (LoginUser) auth.getPrincipal();
                return loginUser;
        }

        public String getLoginUserNo() {
                return getLoginUser().getUserNo();
        }

        public String getLoginUserId() {
                return getLoginUser().getUserId();
        }

        public String getLoginUserMailaddress() {
                return getLoginUser().getMailAddress();
        }

        @Transactional
        public UsersResponseDto createUser(UsersRegisterDto dto) {
                LocalDateTime now = LocalDateTime.now();
                Saiban saiban = saibanRepository.findByTableName("USERS")
                                .orElseThrow(() -> new IllegalArgumentException("採番テーブルの値が存在しません"));
                String newUserNo = saiban.getPrefix() + saiban.getSaibanNo();
                Users user = new Users(
                                newUserNo,
                                dto.getUserId(),
                                passwordEncoder.encode(dto.getPassword()),
                                dto.getMailAddress(),
                                "0",
                                now,
                                now,
                                now);
                usersRepository.registerUser(
                                user.getUserNo(),
                                user.getUserId(),
                                user.getPassword(),
                                user.getMailAddress(),
                                user.getDelFlg(),
                                user.getLastLoginDate(),
                                user.getInsDate(),
                                user.getUpdDate());

                String newSaibanNo = String.format("%08d", (Integer.parseInt(saiban.getSaibanNo()) + 1));

                saibanRepository.updateSaibanNo(newSaibanNo, "USERS");

                return new UsersResponseDto(
                                user.getUserNo(),
                                user.getUserId(),
                                user.getMailAddress(),
                                user.getLastLoginDate());
        }

        public UsersUpdateDto getUserByUserNo(String userNo) {
                Users user = usersRepository.findByUserNo(userNo)
                                .orElseThrow(() -> new IllegalArgumentException("ユーザーテーブルの値が存在しません"));
                UsersUpdateDto dto = new UsersUpdateDto();
                dto.setUserNo(user.getUserNo());
                dto.setUserId(user.getUserId());
                dto.setMailAddress(user.getMailAddress());
                return dto;
        }

        @Transactional
        public void updateUser(UsersUpdateDto dto) {
                usersRepository.updateUser(
                                dto.getUserNo(),
                                dto.getUserId(),
                                dto.getMailAddress());

                if (!(dto.getPassword().isBlank()) && !(dto.getPassword() == null)) {
                        usersRepository.updatePassword(dto.getUserNo(), passwordEncoder.encode(dto.getPassword()));
                }
        }
}
