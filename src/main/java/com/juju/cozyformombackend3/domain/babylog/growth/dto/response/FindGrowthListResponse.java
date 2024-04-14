package com.juju.cozyformombackend3.domain.babylog.growth.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.GrowthSummary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindGrowthListResponse {
    private final String nextExaminationDate;
    private final Long lastId;
    private final Integer size;
    private final List<GrowthSummary> list;

    public static FindGrowthListResponse of(String nextExaminationDate, List<GrowthSummary> list) {
        return new FindGrowthListResponse(nextExaminationDate, list.getLast().getGrowthReportId(), list.size(), list);
    }
}
