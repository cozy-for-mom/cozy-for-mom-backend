package com.juju.cozyformombackend3.global.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.juju.cozyformombackend3.global.auth.handler.AuthExceptionHandler;
import com.juju.cozyformombackend3.global.auth.handler.OAuth2SuccessHandler;
import com.juju.cozyformombackend3.global.auth.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oauth2SuccessHandler;
    private final AuthExceptionHandler authExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(configurer -> configurer.anyRequest().permitAll());
        http.oauth2Login(configurer -> configurer
            .successHandler(oauth2SuccessHandler)
            .failureHandler(authExceptionHandler)
            .userInfoEndpoint(
                userInfoEndpointConfigurer -> userInfoEndpointConfigurer.userService(customOAuth2UserService)));
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
