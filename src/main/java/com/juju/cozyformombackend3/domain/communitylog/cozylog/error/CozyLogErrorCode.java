package com.juju.cozyformombackend3.domain.communitylog.cozylog.error;

import org.springframework.http.HttpStatus;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CozyLogErrorCode implements ErrorCode {
	NOT_FOUND_COZY_LOG(404, "해당 cozy-log를 찾을 수 없습니다."),
	FORBIDDEN_INACCESSIBLE(HttpStatus.FORBIDDEN.value(), "접근할 수 없는 cozy-log 입니다. ");

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
