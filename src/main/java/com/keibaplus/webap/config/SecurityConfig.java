package com.keibaplus.webap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.keibaplus.webap.service.CustomUserDetailsService;

/**
 * Spring Security関連の設定クラス
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
        // 必要なインスタンスを使用
        private final CustomUserDetailsService customUserDetailsService;
        private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
        private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
        private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
        private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

        // コンストラクタ
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

        /**
         * SecurityFilterChainのBean定義
         * 
         * @param http HttpSecurityインスタンス
         * @return 構築されたSecurityFilterChainオブジェクト
         * @throws Exception
         */
        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                // ユーザー登録前やログイン前にアクセスできるような画面を設定するため、エンドポイントの認可を設定
                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/notlogin",
                                                "/welcome",
                                                "/login",
                                                "/register",
                                                "/error",
                                                "/termsOfUse",
                                                "/privacyPolicy",
                                                "/userdeletesuccess",
                                                "/css/**",
                                                "/js/**",
                                                "/images/**",
                                                "**/favicon**",
                                                "/site.webmanifest")
                                .permitAll()
                                .anyRequest().authenticated())
                                // ログイン機能の設定
                                .formLogin(login -> login
                                                .loginPage("/login")
                                                .loginProcessingUrl("/login")
                                                .successHandler(customAuthenticationSuccessHandler)
                                                .failureHandler(customAuthenticationFailureHandler)
                                                .permitAll())
                                // ログアウト機能の設定
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessHandler(customLogoutSuccessHandler)
                                                .permitAll())
                                // 認証の設定
                                .authenticationProvider(authenticationProvider())
                                // 未認証アクセス時の設定
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(customAuthenticationEntryPoint));
                // 構築されたSecurityFilterChainオブジェクトをreturn
                return http.build();
        }

        /**
         * DaoAuthenticationProviderのBean定義
         * 
         * @return 認証情報
         */
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                // パスワード暗号化を使用するためにPasswordConfigのインスタンスを取得
                PasswordConfig passwordConfig = new PasswordConfig();
                // 認証のためにCustomUserDetailsServiceのインスタンスを使用
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
                // パスワード暗号化を使用
                provider.setPasswordEncoder(passwordConfig.passwordEncoder());
                // 認証情報をreturn
                return provider;
        }

}
