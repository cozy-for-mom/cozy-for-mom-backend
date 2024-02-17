package com.juju.cozyformombackend3.global.dto.response;

import java.time.LocalDateTime;

import com.juju.cozyformombackend3.global.util.DateParser;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

	private final String timestamp;
	private final int status;
	private final String message;

	@Builder
	public ErrorResponse(LocalDateTime timestamp, int status, String message) {
		this.timestamp = DateParser.dateTimeToString(timestamp);
		this.status = status;
		this.message = message;
	}

	public static ErrorResponse of(LocalDateTime timestamp, int status, String message) {
		return ErrorResponse.builder()
			.timestamp(timestamp)
			.status(status)
			.message(message)
			.build();
	}
}
