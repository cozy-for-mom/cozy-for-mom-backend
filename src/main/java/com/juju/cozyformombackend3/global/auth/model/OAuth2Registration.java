package com.juju.cozyformombackend3.global.auth.model;

import java.util.Arrays;

import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

public enum OAuth2Registration {
    KAKAO("kakao"),
    APPLE("apple");

    private final String registrationName;

    OAuth2Registration(String registrationName) {
        this.registrationName = registrationName;
    }

    public static OAuth2Registration ofType(String registrationName) {
        return Arrays.stream(OAuth2Registration.values())
            .filter(e -> e.getRegistrationName().equals(registrationName))
            .findAny()
            .orElseThrow(() -> new BusinessException(AuthErrorCode.NOT_FOUND_NOT_MATCH_OAUTH2));
    }

    public String getRegistrationName() {
        return registrationName;
    }
}
