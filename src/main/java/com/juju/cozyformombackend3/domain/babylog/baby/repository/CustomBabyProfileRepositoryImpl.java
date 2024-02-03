package com.juju.cozyformombackend3.domain.babylog.baby.repository;

import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.FindGrowthDiaryRecord;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CustomBabyProfileRepositoryImpl implements CustomBabyProfileRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public FindGrowthDiaryRecord searchGrowthRecord(Long userId, LocalDate date) {
		//        return queryFactory.select(new QFindGrowthDiaryRecord(
		//                        babyProfile.babyProfileId,
		//                        babyProfile.growthDiaryList.,
		//                        babyProfile.babyList
		//                ))
		//                .from(babyProfile)
		//                .where(babyProfile.user.userId.eq(userId), (babyProfile.date.eq(date)))
		//                .fetchOne();

		return null;
	}
}
