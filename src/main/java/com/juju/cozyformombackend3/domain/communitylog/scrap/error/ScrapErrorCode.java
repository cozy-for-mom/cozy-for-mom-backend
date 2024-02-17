package com.juju.cozyformombackend3.domain.communitylog.scrap.error;

import org.springframework.http.HttpStatus;

import com.juju.cozyformombackend3.global.error.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScrapErrorCode implements ErrorCode {
	CONFLICT_ALREADY_EXIST(HttpStatus.CONFLICT.value(), "이미 스크랩한 cozy-log 입니다."),
	NOT_FOUND_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "스크랩하지 않은 cozy-log 입니다.");

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
