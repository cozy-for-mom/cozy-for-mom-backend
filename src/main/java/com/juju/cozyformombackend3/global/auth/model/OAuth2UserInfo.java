package com.juju.cozyformombackend3.global.auth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuth2UserInfo {
    private final String email;
    private final String profileImage;
    private final String oauthValue;

    public static OAuth2UserInfo of(String email, String profileImage, String oauthValue) {
        return new OAuth2UserInfo(email, profileImage, oauthValue);
    }
}
