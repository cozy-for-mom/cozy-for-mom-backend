package com.juju.cozyformombackend3.domain.babylog.growth.dto.object;

import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class GrowthSummary {
    private final Long growthReportId;
    private final String date;
    private final String growthImageUrl;
    private final String title;
    private final String content;

    @QueryProjection
    public GrowthSummary(Long id, LocalDate date, String imageUrl, String title, String content) {
        this.growthReportId = id;
        this.date = date.toString();
        this.growthImageUrl = imageUrl;
        this.title = title;
        this.content = content;
    }
}
