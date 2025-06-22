package br.com.petsalus.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // permite em todos os endpoints
                .allowedOrigins("*") // permite todas as origens
                .allowedMethods("*") // permite todos os métodos (GET, POST, PUT, DELETE, etc.)
                .allowedHeaders("*") // permite todos os headers
                .allowCredentials(false); // sem cookies/autenticação
    }
}