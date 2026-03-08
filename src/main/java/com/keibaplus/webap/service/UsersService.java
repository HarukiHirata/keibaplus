package com.keibaplus.webap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keibaplus.webap.dto.UsersRequestDto;
import com.keibaplus.webap.dto.UsersResponseDto;
import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.entity.Users;
import com.keibaplus.webap.repository.UsersRepository;

import java.time.LocalDateTime;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
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

    @Transactional
    public UsersResponseDto createUser(UsersRegisterDto dto) {
        LocalDateTime now = LocalDateTime.now();
        Users user = new Users(
                "US00000001",
                dto.getUserId(),
                dto.getPassword(),
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

        return new UsersResponseDto(
                user.getUserNo(),
                user.getUserId(),
                user.getMailAddress(),
                user.getLastLoginDate());
    }
}
