package com.juju.cozyformombackend3.global.auth.error;

import org.springframework.http.HttpStatus;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    NOT_FOUND_NOT_MATCH_OAUTH2(HttpStatus.NOT_FOUND.value(), "지원하지 않는 소셜 로그인입니다."),
    UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL(HttpStatus.UNAUTHORIZED.value(), "소셜 서비스에서 사용자의 이메일 정보를 찾을 수 없습니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "존재하지 않는 회원입니다."),
    CONFLICT_EXIST_NICKNAME(HttpStatus.CONFLICT.value(), "이미 사용중인 닉네임입니다."),
    CONFLICT_EXIST_EMAIL(HttpStatus.CONFLICT.value(), "이미 해당 이메일로 회원가입된 계정이 존재합니다."),
    CONFLICT_EXIST_OAUTH_ACCOUNT(HttpStatus.CONFLICT.value(), "이미 회원가입된 소셜 로그인 계정입니다."),
    NOT_FOUND_PRIVATE_KEY(HttpStatus.NOT_FOUND.value(), "개인키를 찾을 수 없습니다."),
    SERVER_ERROR_CANT_READ_PRIVATE_KEY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "개인키를 읽을 수 없습니다."),
    SERVER_ERROR_CANT_CONVERT_PRIVATE_KEY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "개인키를 변환할 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 사용자입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),
    SERVER_ERROR_UNLINK_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "소셜 계정 연동 해제에 실패했습니다."),
    ;

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
