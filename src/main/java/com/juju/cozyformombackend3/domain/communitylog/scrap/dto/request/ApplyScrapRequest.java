package com.juju.cozyformombackend3.domain.communitylog.scrap.dto.request;

import com.juju.cozyformombackend3.domain.communitylog.scrap.model.Scrap;
import com.juju.cozyformombackend3.domain.user.model.User;

import lombok.Getter;

@Getter
public class ApplyScrapRequest {
	private Long cozyLogId;
	private Boolean isScraped;

	public Scrap toEntity(User user) {
		return Scrap.builder()
			.user(user)
			.cozyLogId(cozyLogId)
			.build();
	}
}
