package com.juju.cozyformombackend3.global.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.juju.cozyformombackend3.global.auth.filter.CheckLogoutTokenFilter;
import com.juju.cozyformombackend3.global.auth.filter.JwtFilter;
import com.juju.cozyformombackend3.global.auth.handler.AuthExceptionHandler;
import com.juju.cozyformombackend3.global.auth.service.token.CozyTokenProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthExceptionHandler authExceptionHandler;
    private final CozyTokenProvider cozyTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.exceptionHandling(exceptionConfig -> exceptionConfig
            .authenticationEntryPoint(authExceptionHandler)
            .accessDeniedHandler(authExceptionHandler));
        http.authorizeHttpRequests(configurer -> configurer
            .requestMatchers(HttpMethod.GET, "api/v1/me").hasAuthority("ROLE_USER")
            .requestMatchers(HttpMethod.DELETE, "api/v1/user/logout").hasAuthority("ROLE_USER")
            .requestMatchers(HttpMethod.POST, "api/v1/user/signup").hasAuthority("ROLE_GUEST")
            .anyRequest().permitAll());
        http.addFilterBefore(new JwtFilter(cozyTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CheckLogoutTokenFilter(redisTemplate, authExceptionHandler), JwtFilter.class);
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();

        // .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
