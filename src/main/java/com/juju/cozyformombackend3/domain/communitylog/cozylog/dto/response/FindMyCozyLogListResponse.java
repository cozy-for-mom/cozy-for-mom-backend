package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindMyCozyLogListResponse {
	private final Long totalCount;
	private final List<CozyLogSummary> cozyLogs;

	public static FindMyCozyLogListResponse of(Long count, List<CozyLogSummary> cozyLogs) {
		return new FindMyCozyLogListResponse(count, cozyLogs);
	}
}
