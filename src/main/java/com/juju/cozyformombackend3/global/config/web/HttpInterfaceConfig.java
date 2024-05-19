package com.juju.cozyformombackend3.global.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import com.juju.cozyformombackend3.global.auth.service.registration.client.AppleApiClient;
import com.juju.cozyformombackend3.global.auth.service.registration.client.KakaoApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class HttpInterfaceConfig {
    private final HttpInterfaceFactory httpInterfaceFactory;

    @Bean
    public KakaoApiClient kakaoApiClient() {
        return httpInterfaceFactory.create(KakaoApiClient.class, createRestClient());
    }

    @Bean
    public AppleApiClient appleApiClient() {
        return httpInterfaceFactory.create(AppleApiClient.class, createRestClient());
    }

    private RestClient createRestClient() {
        return RestClient.builder()
            // .baseUrl(baseUrl)
            .defaultStatusHandler(
                HttpStatusCode::is4xxClientError,
                (request, response) -> {
                    log.error("Client Error Code={}", response.getStatusCode());
                    log.error("Client Error Message={}", new String(response.getBody().readAllBytes()));
                })
            .defaultStatusHandler(
                HttpStatusCode::is5xxServerError,
                (request, response) -> {
                    log.error("Server Error Code={}", response.getStatusCode());
                    log.error("Server Error Message={}", new String(response.getBody().readAllBytes()));
                })
            .build();
    }
}
