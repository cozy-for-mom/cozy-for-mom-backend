package com.juju.cozyformombackend3.global.auth.dto;

import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class CheckOAuthAccountDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String email;
        private String oauthId;
        private String oauthType;

        public OAuth2Registration getOauthType() {
            return OAuth2Registration.ofType(oauthType);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final String email;
        private final String oauthId;
        private final String oauthType;

        public static Response of(Request request) {
            return new Response(request.email, request.oauthId, request.oauthType);
        }
    }
}
