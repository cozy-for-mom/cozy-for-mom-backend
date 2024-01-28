package com.juju.cozyformombackend3.domain.supplement.dto.object;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FindDailySupplementIntake {
    private final Long supplementId;
    private final String supplementName;
    private final int targetCount;
    private final LocalDateTime datetime;

    @QueryProjection
    public FindDailySupplementIntake(Long supplementId, String supplementName, int targetCount, LocalDateTime datetime) {
        this.supplementId = supplementId;
        this.supplementName = supplementName;
        this.targetCount = targetCount;
        this.datetime = datetime;
    }
}
