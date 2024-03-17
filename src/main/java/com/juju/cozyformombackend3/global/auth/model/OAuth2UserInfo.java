package com.juju.cozyformombackend3.global.auth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuth2UserInfo {
    private final String email;
}
