package com.juju.cozyformombackend3.domain.userlog.meal.error;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MealErrorCode implements ErrorCode {

	NOT_FOUND_MEAL_RECORD(404, "존재하지 않는 식사 기록입니다."),
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