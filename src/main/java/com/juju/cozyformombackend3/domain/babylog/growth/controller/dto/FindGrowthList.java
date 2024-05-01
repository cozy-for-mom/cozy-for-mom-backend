package com.juju.cozyformombackend3.domain.babylog.growth.controller.dto;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.GrowthSummary;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FindGrowthList {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private final String nextExaminationDate;
        private final Long lastId;
        private final Integer size;
        private final List<GrowthSummary> list;

        public static Response of(String nextExaminationDate, List<GrowthSummary> list) {
            return new Response(nextExaminationDate, list.getLast().getGrowthReportId(), list.size(),
                list);
        }
    }
}

