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
        private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
        private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
        private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
        private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

        public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                        CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                        CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
                        CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                        CustomLogoutSuccessHandler customLogoutSuccessHandler) {
                this.customUserDetailsService = customUserDetailsService;
                this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
                this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
                this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
                this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/notlogin", "/welcome", "/login", "/register", "/error", "/css/**",
                                                "/api/**",
                                                "/js/**",
                                                "/images/**", "**/favicon**")
                                .permitAll()
                                .anyRequest().authenticated())
                                .formLogin(login -> login
                                                .loginPage("/login")
                                                .loginProcessingUrl("/login")
                                                .successHandler(customAuthenticationSuccessHandler)
                                                .failureHandler(customAuthenticationFailureHandler)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessHandler(customLogoutSuccessHandler)
                                                .permitAll())
                                .authenticationProvider(authenticationProvider())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(customAuthenticationEntryPoint));
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
