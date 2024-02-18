package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;
import com.juju.cozyformombackend3.global.dto.PageMetaData;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetCozyLogListResponse {
	private final List<CozyLogSummary> cozyLogs;
	private final PageMetaData pageMetaData;

	public static GetCozyLogListResponse of(Slice slice) {
		return new GetCozyLogListResponse(slice.getContent(), PageMetaData.of(slice));
	}
}
