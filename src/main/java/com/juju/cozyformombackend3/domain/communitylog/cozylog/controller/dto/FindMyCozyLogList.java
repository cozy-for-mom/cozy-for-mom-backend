package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.dto;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FindMyCozyLogList {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final Long totalCount;
        private final List<CozyLogSummary> cozyLogs;

        public static Response of(Long count, List<CozyLogSummary> cozyLogs) {
            return new Response(count, cozyLogs);
        }
    }
}