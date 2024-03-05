package com.juju.cozyformombackend3.domain.user.repository;

import static com.juju.cozyformombackend3.domain.babylog.baby.model.QBabyProfile.*;
import static com.juju.cozyformombackend3.domain.user.model.QUser.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.user.dto.object.QUserSummary;
import com.juju.cozyformombackend3.domain.user.dto.object.UserSummary;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public UserSummary findUserSummaryById(Long userId) {
		List<BabyProfile> babyProfiles = jpaQueryFactory
			.select(babyProfile)
			.from(babyProfile)
			.where(babyProfile.user.id.eq(userId))
			.fetch();

		UserSummary summary = (UserSummary)jpaQueryFactory.select(new QUserSummary(
				user.name, user.nickname, user.introduce, user.profileImageUrl, user.birth.stringValue(),
				user.email, user.recentBabyProfileId))
			.from(user)
			.leftJoin(user.babyProfileList, babyProfile)
			.where(user.id.eq(userId))
			.fetch();

		summary.setBabyProfile(babyProfiles);
		return summary;
		// return null;
	}
}
