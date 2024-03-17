package com.juju.cozyformombackend3.global.auth.error;

import org.springframework.http.HttpStatus;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    NOT_FOUND_NOT_MATCH_OAUTH2(HttpStatus.NOT_FOUND.value(), "지원하지 않는 소셜 로그인입니다.");

    private final int status;
    private final String message;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
