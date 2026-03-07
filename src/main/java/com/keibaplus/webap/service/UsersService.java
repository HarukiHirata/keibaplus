package com.keibaplus.webap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keibaplus.webap.dto.UsersRequestDto;
import com.keibaplus.webap.dto.UsersResponseDto;
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
    public UsersResponseDto createUser(UsersRequestDto dto) {
        LocalDateTime now = LocalDateTime.now();
        Users user = new Users(
                dto.getUserNo(),
                dto.getUserId(),
                "password",
                dto.getMailAddress(),
                "0",
                now,
                now,
                now);
        Users savedUser = usersRepository.save(user);

        return new UsersResponseDto(
                savedUser.getUserNo(),
                savedUser.getUserId(),
                savedUser.getMailAddress(),
                savedUser.getLastLoginDate());
    }
}
