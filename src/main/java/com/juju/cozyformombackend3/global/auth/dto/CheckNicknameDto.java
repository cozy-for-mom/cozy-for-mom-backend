package com.juju.cozyformombackend3.global.auth.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class CheckNicknameDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @Parameter(required = true)
        private String nickname;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final String nickname;

        public static Response of(final String nickname) {
            return new Response(nickname);
        }
    }
}
