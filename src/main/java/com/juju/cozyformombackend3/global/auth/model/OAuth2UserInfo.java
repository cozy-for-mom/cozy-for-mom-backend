package com.juju.cozyformombackend3.global.auth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuth2UserInfo {
    private final String oauthId;
    private final String email;
    private final String profileImage;
    private final String nickname;
    private final UserRole role;

    public static OAuth2UserInfo of(String oauthId, String email, String profileImage, String nickname,
        UserRole role) {
        return new OAuth2UserInfo(oauthId, email, profileImage, nickname, role);
    }
}
