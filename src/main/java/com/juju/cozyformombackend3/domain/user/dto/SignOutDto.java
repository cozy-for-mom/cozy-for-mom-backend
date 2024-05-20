package com.juju.cozyformombackend3.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class SignOutDto {

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

        public static SignOutDto.Response of(final Long userId) {
            return new SignOutDto.Response(userId);
        }
    }
}
