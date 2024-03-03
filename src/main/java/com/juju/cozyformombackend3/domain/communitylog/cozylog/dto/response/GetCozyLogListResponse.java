package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetCozyLogListResponse {
	private final List<CozyLogSummary> cozyLogs;

	public static GetCozyLogListResponse of(List<CozyLogSummary> cozyLogs) {
		return new GetCozyLogListResponse(cozyLogs);
	}
}
