package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.dto;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SearchCozyLog {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final Long totalCount;
        private final List<CozyLogSummary> results;

        public static Response of(final Long totalCount, final List<CozyLogSummary> results) {
            return new Response(totalCount, results);
        }
    }

}