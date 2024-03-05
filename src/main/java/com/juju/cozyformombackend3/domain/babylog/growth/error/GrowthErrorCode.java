package com.juju.cozyformombackend3.domain.babylog.growth.error;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GrowthErrorCode implements ErrorCode {
	NOT_FOUND_GROWTH_REPORT(404, "존재하지 않는 성장 보고서입니다."),
	NOT_FOUND_GROWTH_RECORD(404, "존재하지 않는 성장 기록입니다."),
	NOT_FOUND_GROWTH_DIARY(404, "존재하지 않는 성장 일기입니다."),
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
