package com.juju.cozyformombackend3.domain.userlog.bloodsugar.error;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BloodSugarErrorCode implements ErrorCode {
	NOT_FOUND_BLOODSUGAR_RECORD(404, "존재하지 않는 혈당 기록입니다."),
	FORBIDDEN_NOT_YOUR_RESOURCE(403, "권한이 없는 리소스 입니다."),
	CONFLICT_ALREADY_EXIST_RECORD(409, "이미 해당 날짜에 해당 타입의 혈당 기록이 존재합니다.");

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

