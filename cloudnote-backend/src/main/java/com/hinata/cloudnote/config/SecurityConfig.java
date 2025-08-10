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

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//          .csrf(csrf -> csrf.disable())
//          .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//          .httpBasic(basic -> basic.disable())
//          .formLogin(login -> login.disable());
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
              .anyRequest().authenticated()
          )
          .formLogin(form -> form
              .loginProcessingUrl("/api/auth/login")           // フロントがPOSTするログインURL
              .usernameParameter("username")
              .passwordParameter("password")
              .successHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
              .failureHandler((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
          )
          .httpBasic(b -> b.disable())
          .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    // 全パス・全オリジンを許可する CORS 設定
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var config = new CorsConfiguration();

        // ワイルドカードでオリジン全許可
        config.setAllowedOrigins(List.of("*"));
        // すべてのHTTPメソッドを許可
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // すべてのヘッダーを許可
        config.setAllowedHeaders(List.of("*"));
        // 認証情報を送らないならfalse
        config.setAllowCredentials(false);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}