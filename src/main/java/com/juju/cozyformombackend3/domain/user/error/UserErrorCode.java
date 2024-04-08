package com.juju.cozyformombackend3.domain.user.error;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(404, "존재하지 않는 사용자입니다."),
    FORBIDDEN_NOT_YOUR_RESOURCE(403, "권한이 없는 리소스 입니다.");

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
