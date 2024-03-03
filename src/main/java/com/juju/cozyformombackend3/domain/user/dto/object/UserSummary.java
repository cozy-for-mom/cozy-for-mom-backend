package com.juju.cozyformombackend3.domain.user.dto.object;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class UserSummary {
	private final String name;
	private final String nickname;
	private final String introduce;
	private final String imageUrl;
	private final String birth;
	private final String email;
	private final Long recentBabyProfileId;
	private List<BabyProfile> babyProfile;

	@QueryProjection
	public UserSummary(String name, String nickname, String introduce, String imageUrl, String birth, String email,
		Long recentBabyProfileId) {
		this.name = name;
		this.nickname = nickname;
		this.introduce = introduce;
		this.imageUrl = imageUrl;
		this.birth = birth;
		this.email = email;
		this.recentBabyProfileId = recentBabyProfileId;
	}

	public void setBabyProfile(List<BabyProfile> babyProfiles) {
		this.babyProfile = babyProfiles;
	}

	public UserSummary(User user) {
		this.name = user.getName();
		this.nickname = user.getNickname();
		this.introduce = user.getIntroduce();
		this.imageUrl = user.getProfileImageUrl();
		this.birth = String.valueOf(user.getBirth());
		this.email = user.getEmail();
		this.recentBabyProfileId = user.getRecentBabyProfileId();
		this.babyProfile = user.getBabyProfileList();
	}
}
