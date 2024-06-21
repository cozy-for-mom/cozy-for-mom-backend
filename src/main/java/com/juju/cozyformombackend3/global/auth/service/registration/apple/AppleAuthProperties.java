package com.juju.cozyformombackend3.global.auth.service.registration.apple;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.apple")
@Getter
@RequiredArgsConstructor
public class AppleAuthProperties {
    private final String keyId;
    private final String teamId;
    private final String clientId;
    private final Long clientSecretExp;
    private final String authKeyPath;
    private final String aud;
}
