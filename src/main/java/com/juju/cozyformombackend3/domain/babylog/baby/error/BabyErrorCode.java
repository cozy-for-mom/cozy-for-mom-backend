package com.juju.cozyformombackend3.domain.babylog.baby.error;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BabyErrorCode implements ErrorCode {
	NOT_FOUND_BABY_PROFILE(404, "존재하지 않는 아이 프로필입니다."),
	NOT_FOUND_BABY(404, "존재하지 않는 아이입니다."),
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
