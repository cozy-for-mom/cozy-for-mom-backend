package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.dto;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FindCozyLogList {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final List<CozyLogSummary> cozyLogs;

        public static Response of(List<CozyLogSummary> cozyLogs) {
            return new Response(cozyLogs);
        }
    }
}

