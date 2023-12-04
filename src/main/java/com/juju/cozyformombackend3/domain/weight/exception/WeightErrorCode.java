package com.juju.cozyformombackend3.domain.weight.exception;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WeightErrorCode implements ErrorCode {
	RECORD_DATE_ALREADY_EXISTS(409, "이미 해당 날짜의 기록이 존재합니다.");

	private final int status;
	private final String message;

	@Override
	public int getStatus() {
		return 0;
	}

	@Override
	public String getMessage() {
		return null;
	}
}
