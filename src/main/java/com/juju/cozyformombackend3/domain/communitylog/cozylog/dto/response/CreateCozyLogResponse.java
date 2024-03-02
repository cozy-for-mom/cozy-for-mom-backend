package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateCozyLogResponse {
	private final Long cozyLogId;

	public static CreateCozyLogResponse of(Long cozyLogId) {
		return new CreateCozyLogResponse(cozyLogId);
	}
}
