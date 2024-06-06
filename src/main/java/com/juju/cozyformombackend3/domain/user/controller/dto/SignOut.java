package com.juju.cozyformombackend3.domain.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class SignOut {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String reason;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long userId;

        public static SignOut.Response of(final Long userId) {
            return new SignOut.Response(userId);
        }
    }
}
