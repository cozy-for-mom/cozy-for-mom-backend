package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;
import com.juju.cozyformombackend3.global.dto.CustomSlice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchCozyLogResponse {
    private final Long totalCount;
    private final List<CozyLogSummary> results;

    public static SearchCozyLogResponse of(CustomSlice<CozyLogSummary> cozyLogs) {
        return new SearchCozyLogResponse(cozyLogs.getTotalCount(), cozyLogs.getData());
    }
}
