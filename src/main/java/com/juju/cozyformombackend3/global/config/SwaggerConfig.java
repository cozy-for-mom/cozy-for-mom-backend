package com.juju.cozyformombackend3.global.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    // public static final String API_TITLE = "cozy-for-mom Swagger API";
    // public static final String API_DESCRIPTION = "cozy-for-mom API 명세서입니다.";
    // public static final String API_VERSION = "v0.1";
    // public static final String API_GROUP_NAME = "cozy";
    //
    // @Bean
    // public OpenAPI openApi() {
    // 	return new OpenAPI()
    // 		.info(new Info().title(API_TITLE)
    // 			.description(API_DESCRIPTION)
    // 			.version(API_VERSION)
    // 		);
    // }

    // private static final String BEARER_TOKEN_PREFIX = "Bearer";

    // @Bean
    // public OpenAPI openAPI() {
    // String jwtSchemeName = JwtTokenProvider.AUTHORIZATION_HEADER;
    // SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
    // 	Components components = new Components()
    // 		.addSecuritySchemes(jwtSchemeName, new SecurityScheme()
    // 			.name(jwtSchemeName)
    // 			.type(SecurityScheme.Type.HTTP)
    // 			.scheme(BEARER_TOKEN_PREFIX)
    // 			.bearerFormat(JwtTokenProvider.TYPE));
    //
    // 	// Swagger UI 접속 후, 딱 한 번만 accessToken을 입력해주면 모든 API에 토큰 인증 작업이 적용됩니다.
    // 	return new OpenAPI()
    // 		.addSecurityItem(securityRequirement)
    // 		.components(components);
    // }
}
