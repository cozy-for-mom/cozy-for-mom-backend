package com.juju.cozyformombackend3.domain.userlog.weight.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.weight.dto.object.FindPeriodicWeight;
import com.juju.cozyformombackend3.global.dto.request.RecordPeriod;
import com.juju.cozyformombackend3.global.util.DateParser;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindWeightListResponse {
    private final String type;
    private final Double todayWeight;
    private final String lastRecordDate;
    private final List<FindPeriodicWeight> weightList;

    public static FindWeightListResponse of(RecordPeriod type, Double todayWeight, LocalDate lastRecordDate,
        List<FindPeriodicWeight> findPeriodicWeights) {
        return new FindWeightListResponse(type.getPeriodKeyword(), todayWeight,
            DateParser.localDateToStringFormatDate(lastRecordDate), findPeriodicWeights);
    }
}
