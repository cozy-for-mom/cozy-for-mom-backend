package com.juju.cozyformombackend3.domain.userlog.weight.repository;

import static com.juju.cozyformombackend3.domain.userlog.weight.model.QWeightRecord.*;
import static com.juju.cozyformombackend3.global.repository.DateParser.*;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.weight.dto.object.FindPeriodicWeight;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.object.QFindPeriodicWeight;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomWeightRepositoryImpl implements CustomWeightRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<FindPeriodicWeight> findPeriodRecordByDate(FindPeriodRecordCondition condition) {
		return switch (condition.getType()) {
			case MONTHLY -> findMonthlyRecordByDate(condition.getDate(), condition.getSize());
			case WEEKLY -> findWeeklyRecordByDate(condition.getDate(), condition.getSize());
			default -> findDailyRecordByDate(condition.getDate(), condition.getSize());
		};
	}

	private List<FindPeriodicWeight> findMonthlyRecordByDate(LocalDate endDate, Long size) {
		return jpaQueryFactory.select(new QFindPeriodicWeight(
				getDateFromDateTime(weightRecord.createdAt.max()),
				weightRecord.weight.avg()))
			.from(weightRecord)
			.where(weightRecord.createdAt
				.between(endDate.minusMonths(size).withDayOfMonth(1).atStartOfDay(),
					endDate.atTime(23, 59, 59)))
			.groupBy(weightRecord.createdAt.month())
			.fetch();
	}

	private List<FindPeriodicWeight> findWeeklyRecordByDate(LocalDate endDate, Long size) {

		return jpaQueryFactory.select(new QFindPeriodicWeight(
				getDateFromDateTime(weightRecord.createdAt.max()),
				weightRecord.weight.avg()))
			.from(weightRecord)
			.where(weightRecord.createdAt
				.between(endDate.minusWeeks(size).atStartOfDay(),
					endDate.atTime(23, 59, 59)))
			.groupBy(weightRecord.createdAt.week())
			.fetch();
	}

	private List<FindPeriodicWeight> findDailyRecordByDate(LocalDate endDate, Long size) {
		return jpaQueryFactory.select(new QFindPeriodicWeight(
				getDateFromDateTime(weightRecord.createdAt),
				weightRecord.weight.avg()))
			.from(weightRecord)
			.where(weightRecord.createdAt
				.between(endDate.minusDays(size).atStartOfDay(),
					endDate.atTime(23, 59, 59)))
			.groupBy(weightRecord.createdAt)
			.fetch();
	}
}
