package com.juju.cozyformombackend3.global.error.exception;

import org.springframework.security.core.AuthenticationException;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.Getter;

@Getter
public class AuthException extends AuthenticationException {
    private final transient ErrorCode errorCode;

    public AuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
