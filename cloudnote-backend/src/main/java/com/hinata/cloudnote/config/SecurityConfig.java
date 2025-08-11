package com.hinata.cloudnote.config;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF無効化（API向けのため）
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll() // 認証不要エンドポイント
                .anyRequest().authenticated() // その他は認証必須
            )
            .formLogin(form -> form
                .loginProcessingUrl("/api/auth/login") // ログイン処理URL（POSTのみ）
                .usernameParameter("username") // ユーザー名パラメータ
                .passwordParameter("password") // パスワードパラメータ
                .successHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK)) // 成功時200
                .failureHandler((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED)) // 失敗時401
            )
            .httpBasic(b -> b.disable()) // Basic認証は使用しない
            .cors(cors -> cors.configurationSource(corsConfigurationSource())); // CORS設定

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager(); // 認証マネージャー取得
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("*")); // 全オリジン許可
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 全HTTPメソッド許可
        config.setAllowedHeaders(List.of("*")); // 全ヘッダー許可
        config.setAllowCredentials(false); // 認証情報は送らない

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 全パスにCORS設定を適用
        return source;
    }
}
