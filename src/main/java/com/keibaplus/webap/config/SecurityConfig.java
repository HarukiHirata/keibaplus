package com.keibaplus.webap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.keibaplus.webap.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        private final CustomUserDetailsService customUserDetailsService;

        public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
                this.customUserDetailsService = customUserDetailsService;
        }

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
                                                .loginProcessingUrl("/login")
                                                .defaultSuccessUrl("/top", true)
                                                .failureUrl("/login?error")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll())
                                .authenticationProvider(authenticationProvider())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(
                                                                new LoginUrlAuthenticationEntryPoint("/notlogin")));
                return http.build();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                PasswordConfig passwordConfig = new PasswordConfig();
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
                provider.setPasswordEncoder(passwordConfig.passwordEncoder());
                return provider;
        }

}
