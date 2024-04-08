package com.juju.cozyformombackend3.global.auth.dto.api;

import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SignInDto {

    @Getter
    @AllArgsConstructor
    public static class Request {
        private final String oauthId;
        private final String oauthType;

        public OAuth2Registration getOAuthType() {
            return OAuth2Registration.ofType(oauthType);
        }
    }

    public class Response {

    }
}
