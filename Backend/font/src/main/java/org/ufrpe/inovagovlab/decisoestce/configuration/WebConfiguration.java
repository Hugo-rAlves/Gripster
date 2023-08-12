package org.ufrpe.inovagovlab.decisoestce.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings (CorsRegistry registry) {
        registry.addMapping ("/api/v1/app/**")
                .allowedMethods ("*")
                .allowedOrigins ("http://localhost:4200");
    }
}
