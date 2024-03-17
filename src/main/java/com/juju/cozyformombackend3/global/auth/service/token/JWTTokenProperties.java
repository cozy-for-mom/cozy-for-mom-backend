package com.juju.cozyformombackend3.global.auth.service.token;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "jwt")
@Getter
@RequiredArgsConstructor
public class JWTTokenProperties {
    private final String issuer;
    private final String secretKey;
    private final Long userExpirationTime;
    private final Long guestExpirationTime;
}
