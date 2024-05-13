package com.juju.cozyformombackend3.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
// @OpenAPIDefinition
public class SwaggerConfig {
    public static final String API_TITLE = "cozy-for-mom Swagger API";
    public static final String API_DESCRIPTION = "cozy-for-mom API 명세서입니다.";
    public static final String API_VERSION = "v0.1";
    public static final String API_GROUP_NAME = "cozy";

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    @Bean
    public OpenAPI api() {
        SecurityScheme apiKey = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER)
            .scheme("bearer")
            .bearerFormat("JWT")
            .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList("Bearer Token");

        return new OpenAPI()
            .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
            .addSecurityItem(securityRequirement)
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title(API_TITLE) // API의 제목
            .description(API_DESCRIPTION) // API에 대한 설명
            .version(API_VERSION); // API의 버전
    }
}
