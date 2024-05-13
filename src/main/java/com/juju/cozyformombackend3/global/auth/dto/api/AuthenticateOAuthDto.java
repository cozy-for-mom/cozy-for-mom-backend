package com.juju.cozyformombackend3.global.auth.dto.api;

import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticateOAuthDto {

    @Getter
    @NoArgsConstructor
    public static class Request {
        @Parameter(required = true)
        private String oauthType;
        @Parameter(required = true)
        private String value;
        @Parameter(required = true)
        private String deviceToken;

        public OAuth2Registration getOAuthType() {
            return OAuth2Registration.ofType(oauthType);
        }
    }
}
