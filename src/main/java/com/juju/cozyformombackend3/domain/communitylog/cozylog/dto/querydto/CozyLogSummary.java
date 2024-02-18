package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto;

import java.time.LocalDateTime;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;
import com.juju.cozyformombackend3.global.util.DateParser;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class CozyLogSummary {
	private final Long id;
	private final String title;
	private final String summary;
	private final String date;
	private final String mode;
	private final Long commentCount;
	private final Long scrapCount;
	private final String imageUrl;
	private final Long imageCount;

	@QueryProjection
	public CozyLogSummary(Long id, String title, String summary, LocalDateTime datetime, CozyLogMode mode,
		Long commentCount, Long scrapCount, String imageUrl, Long imageCount) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.date = DateParser.dateTimeToStringFormatDate(datetime);
		this.mode = mode.name();
		this.commentCount = commentCount;
		this.scrapCount = scrapCount;
		this.imageUrl = imageUrl;
		this.imageCount = imageCount;
	}
}
