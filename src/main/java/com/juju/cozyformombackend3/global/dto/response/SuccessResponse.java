package com.juju.cozyformombackend3.global.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SuccessResponse {

	private final LocalDateTime timestamp;
	private final int status;
	private final String message;
	private final Object data;


	@Builder
	public SuccessResponse(LocalDateTime timestamp, int status, String message, Object data) {
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static SuccessResponse of(LocalDateTime timestamp, int status, String message, Object data) {
		return SuccessResponse.builder()
						.timestamp(timestamp)
						.status(status)
						.message(message)
						.data(data)
						.build();
	}
}
