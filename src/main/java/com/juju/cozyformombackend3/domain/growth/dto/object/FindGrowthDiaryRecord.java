package com.juju.cozyformombackend3.domain.growth.dto.object;

import com.juju.cozyformombackend3.domain.baby.model.Baby;
import com.juju.cozyformombackend3.domain.growth.model.GrowthDiary;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class FindGrowthDiaryRecord {
    private final Long babyProfileId;
    private final GrowthDiary growthDiary;
    private final List<Baby> babies;

    @QueryProjection
    public FindGrowthDiaryRecord(Long babyProfileId, GrowthDiary growthDiary, List<Baby> babies) {
        this.babyProfileId = babyProfileId;
        this.growthDiary = growthDiary;
        this.babies = babies;
    }
}
