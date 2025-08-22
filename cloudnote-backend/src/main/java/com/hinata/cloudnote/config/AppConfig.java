package com.hinata.cloudnote.config;

import jakarta.servlet.SessionCookieConfig;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	ServletContextInitializer cookieInitializer() {
		return servletContext -> {
			SessionCookieConfig cfg = servletContext.getSessionCookieConfig();
			cfg.setDomain(null);
		};
	}

}
