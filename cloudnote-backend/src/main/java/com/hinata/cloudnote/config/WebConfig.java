package com.hinata.cloudnote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Bean
	public DefaultCookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName("JSESSIONID");
		serializer.setUseSecureCookie(true);
		serializer.setSameSite("None");
		serializer.setCookiePath("/");
		// do NOT setDomainName – leave it host-only so proxy works
		return serializer;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// /notesや/notes/123/editなど、ドットで終わらないパスはindex.html へ
		registry.addViewController("/{spring:[a-zA-Z0-9-]+}")
				.setViewName("forward:/");
		registry.addViewController("/**/{spring:[a-zA-Z0-9-]+}")
				.setViewName("forward:/");
		registry.addViewController("/{spring:[a-zA-Z0-9-]+}/**{spring:?!(\\.js|\\.css)$}")
				.setViewName("forward:/");
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setPatternParser(null);
	}
}
