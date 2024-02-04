package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ModifyCozyLogResponse {
	private final Long modifiedCozyLogId;

	public static ModifyCozyLogResponse of(Long modifiedCozyLogId) {
		return new ModifyCozyLogResponse(modifiedCozyLogId);
	}
}
