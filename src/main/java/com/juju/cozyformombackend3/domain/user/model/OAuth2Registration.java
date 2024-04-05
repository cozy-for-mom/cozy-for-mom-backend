package com.juju.cozyformombackend3.domain.user.model;

import java.util.Arrays;

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
            .orElseThrow(() -> null);
    }

    public String getRegistrationName() {
        return registrationName;
    }
}
