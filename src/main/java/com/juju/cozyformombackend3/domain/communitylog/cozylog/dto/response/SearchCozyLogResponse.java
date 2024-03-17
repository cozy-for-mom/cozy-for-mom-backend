package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchCozyLogResponse {
    private final Long totalCount;
    private final List<CozyLogSummary> results;

    public static SearchCozyLogResponse of(final Long totalCount, final List<CozyLogSummary> results) {
        return new SearchCozyLogResponse(totalCount, results);
    }
}
