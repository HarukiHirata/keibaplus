package com.keibaplus.webap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/notlogin", "/welcome", "/login", "/register", "/error", "/css/**",
                                                "/js/**",
                                                "/images/**", "**/favicon**")
                                .permitAll()
                                .anyRequest().authenticated())
                                .formLogin(login -> login
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/top", true)
                                                .permitAll())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(
                                                                new LoginUrlAuthenticationEntryPoint("/notlogin")));
                return http.build();
        }
}
