package com.juju.cozyformombackend3.domain.userlog.supplement.error;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SupplementErrorCode implements ErrorCode {

	NOT_FOUND_SUPPLEMENT(404, "존재하지 않는 영양제입니다."),
	NOT_FOUND_SUPPLEMENT_RECORD(404, "존재하지 않는 영양제 기록입니다."),
	FORBIDDEN_NOT_YOUR_RESOURCE(403, "권한이 없는 리소스 입니다."),
	CONFLICT_ALREADY_REGISTER_SUPPLEMENT(409, "이미 등록된 영양제 입니다.");

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
