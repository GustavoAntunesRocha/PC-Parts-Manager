package br.com.antunes.gustavo.pcpartsproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI baseOpenAPI() {
    	return new OpenAPI().info(new Info().title("PC-Components API").version("1.0.0").description("API to manage PC and components"));
    }
}

