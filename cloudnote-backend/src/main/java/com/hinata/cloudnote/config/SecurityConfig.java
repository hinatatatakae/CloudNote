package com.hinata.cloudnote.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * メインのセキュリティ設定
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		http
				// REST API向けに CSRF を無効化
				.csrf(csrf -> csrf.disable())

				// CORS設定を適用
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.cors(Customizer.withDefaults())

				// 認可ルール定義
				.authorizeHttpRequests(authz -> authz
					    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

					    // 認証不要のエンドポイント
					    .requestMatchers("/api/auth/**").permitAll()
					    .requestMatchers(HttpMethod.GET, "/api/notes/**").permitAll() // ← GETは全員OK

					    // 認証が必要なエンドポイント
					    .requestMatchers("/api/notes/mine").authenticated()
					    .requestMatchers(HttpMethod.POST, "/api/notes/**").authenticated()
					    .requestMatchers(HttpMethod.PUT, "/api/notes/**").authenticated()
					    .requestMatchers(HttpMethod.DELETE, "/api/notes/**").authenticated()

					    // その他は認証必須
					    .anyRequest().authenticated()
					)


				// フォームログインを無効化（APIで制御）
				.formLogin(form -> form.disable())

				// ログアウトエンドポイントの設定
				.logout(logout -> logout
						.logoutUrl("/api/auth/logout")
						.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))

				// 認証エラー時には401を返却
				.exceptionHandling(ex -> ex
						.defaultAuthenticationEntryPointFor(
								new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
								new AntPathRequestMatcher("/api/**")))

				// HTTP Basic認証を無効化
				.httpBasic(basic -> basic.disable());

		return http.build();
	}

	/**
	 * AuthenticationManager Beanの登録
	 */
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/**
	 * CORS設定ソースのBean
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		// フロント開発環境のオリジンを明示
		config.setAllowedOrigins(List.of("https://localhost:5173"));

		// 許可するHTTPメソッド
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		// 許可するすべてのヘッダー
		config.setAllowedHeaders(List.of("*"));

		// Cookie（セッションID）送信を許可
		config.setAllowCredentials(true);

		// CORS設定をすべてのパスに適用
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
}
