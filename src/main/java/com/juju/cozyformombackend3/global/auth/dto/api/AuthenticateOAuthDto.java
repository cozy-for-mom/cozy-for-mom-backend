package com.juju.cozyformombackend3.global.auth.dto.api;

import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticateOAuthDto {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private String oauthType;
        private String value;
        private String deviceToken;

        public OAuth2Registration getOAuthType() {
            return OAuth2Registration.ofType(oauthType);
        }
    }
}
