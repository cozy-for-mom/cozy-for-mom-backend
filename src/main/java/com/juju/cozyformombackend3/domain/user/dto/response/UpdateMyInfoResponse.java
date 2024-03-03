package com.juju.cozyformombackend3.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateMyInfoResponse {
	private final Long userId;

	public static UpdateMyInfoResponse of(final Long userId) {
		return new UpdateMyInfoResponse(userId);
	}
}
