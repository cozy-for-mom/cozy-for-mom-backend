package com.juju.cozyformombackend3.domain.notification.error;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {
    NOT_FOUND_NOTIFICATION(404, "존재하지 않는 알림입니다."),
    ;;

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
