package com.juju.cozyformombackend3.domain.userlog.supplement.repository;

import static com.juju.cozyformombackend3.domain.userlog.supplement.model.QSupplementRecord.*;
import static com.juju.cozyformombackend3.global.repository.DateParser.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.supplement.dto.object.FindDailySupplementIntake;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.object.QFindDailySupplementIntake;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomSupplementRecordRepositoryImpl implements CustomSupplementRecordRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<FindDailySupplementIntake> findDailySupplementIntake(long userId, String date) {
		return queryFactory.select(new QFindDailySupplementIntake(
				supplementRecord.supplement.id,
				supplementRecord.supplement.name,
				supplementRecord.supplement.targetCount,
				supplementRecord.id,
				supplementRecord.recordAt))
			.from(supplementRecord)
			.where(supplementRecord.supplement.user.id.eq(userId)
				.and(getDateFromDateTime(supplementRecord.recordAt).eq(date)))
			.fetch();
	}
}
