package com.hinata.cloudnote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/api/**")
	      .allowedOriginPatterns("*")
	      .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
	      .allowedHeaders("*")
	      .allowCredentials(false);
  }
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    // /notes や /notes/123/edit など、ドットで終わらないパスは index.html へ
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

