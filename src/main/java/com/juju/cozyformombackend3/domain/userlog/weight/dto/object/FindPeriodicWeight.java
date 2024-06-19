package com.juju.cozyformombackend3.domain.userlog.weight.dto.object;

import java.time.LocalDate;

import com.juju.cozyformombackend3.global.util.CalculateUtil;
import com.juju.cozyformombackend3.global.util.DateParser;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class FindPeriodicWeight {
    private final String endDate;
    private final double weight;

    @QueryProjection
    public FindPeriodicWeight(LocalDate endDate, double weight) {
        this.endDate = DateParser.localDateToStringFormatDate(endDate);
        this.weight = CalculateUtil.roundToTwoDecimalPlaces(weight);
    }
}
