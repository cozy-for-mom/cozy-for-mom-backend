package com.juju.cozyformombackend3.global.dto.response;

import java.time.LocalDateTime;

import com.juju.cozyformombackend3.global.util.DateParser;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SuccessResponse<T> {

	private final String timestamp;
	private final int status;
	private final T data;

	@Builder
	public SuccessResponse(int status, T data) {
		this.timestamp = DateParser.dateTimeToString(LocalDateTime.now());
		this.status = status;
		this.data = data;
	}

	public static <T> SuccessResponse of(int status, T data) {
		return SuccessResponse.builder()
			.status(status)
			.data(data)
			.build();
	}
}
