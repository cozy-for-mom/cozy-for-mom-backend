package com.juju.cozyformombackend3.domain.user.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Logout {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long userId;

        public static Response of(final Long userId) {
            return new Response(userId);
        }
    }
}
