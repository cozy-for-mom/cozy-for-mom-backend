package com.juju.cozyformombackend3.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class LogoutDto {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long userId;

        public static Response of(final Long userId) {
            return new Response(userId);
        }
    }
}
