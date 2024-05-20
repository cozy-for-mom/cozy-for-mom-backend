package com.juju.cozyformombackend3.global.auth.service.registration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "oauth2.kakao")
@Getter
@RequiredArgsConstructor
public class KakaoAuthProperties {
    private final String clientId;
    private final String clientSecret;
    private final String appAdminKey;
}
