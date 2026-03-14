package com.keibaplus.webap.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.keibaplus.webap.entity.Users;
import com.keibaplus.webap.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Users user = usersRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが存在しません"));

        return User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .authorities("USER")
                .build();

    }
}
