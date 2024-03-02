package com.juju.cozyformombackend3.domain.babylog.growth.dto.object;

import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class GrowthSummary {
	private final Long id;
	private final String date;
	private final String imageUrl;
	private final String title;
	private final String content;

	@QueryProjection
	public GrowthSummary(Long id, LocalDate date, String imageUrl, String title, String content) {
		this.id = id;
		this.date = date.toString();
		this.imageUrl = imageUrl;
		this.title = title;
		this.content = content;
	}
}
