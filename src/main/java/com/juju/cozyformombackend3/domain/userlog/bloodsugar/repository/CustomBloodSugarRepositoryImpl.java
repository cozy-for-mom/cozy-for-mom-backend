package com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository;

import static com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.QBloodSugarRecord.*;
import static com.juju.cozyformombackend3.global.repository.DateParser.*;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodBloodSugarCondition;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.QFindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.QFindPeriodicBloodSugar;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomBloodSugarRepositoryImpl implements CustomBloodSugarRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<FindDaliyBloodSugar> searchAllByCreatedAt(Long userId, String createdAt) {
		return jpaQueryFactory
			.select(new QFindDaliyBloodSugar(bloodSugarRecord.bloodSugarId, bloodSugarRecord.bloodSugarRecordType,
				bloodSugarRecord.level))
			.from(bloodSugarRecord)
			.where(bloodSugarRecord.user.userId.eq(userId),
				getDateFromDateTime(bloodSugarRecord.createdAt).eq(createdAt))
			.orderBy(bloodSugarRecord.createdAt.asc())
			.fetch();
	}

	@Override
	public List<FindPeriodicBloodSugar> findPeriodRecordByDate(FindPeriodBloodSugarCondition condition) {
		LocalDate endDate = LocalDate.parse(condition.getDate());
		Long size = condition.getSize();

		return switch (condition.getType()) {
			case MONTHLY -> findMonthlyRecordByDate(endDate, size);
			case WEEKLY -> findWeeklyRecordByDate(endDate, size);
			default -> findDailyRecordByDate(endDate, size);
		};
	}

	private List<FindPeriodicBloodSugar> findMonthlyRecordByDate(LocalDate endDate, Long size) {
		return jpaQueryFactory.select(new QFindPeriodicBloodSugar(
				getDateFromDateTime(bloodSugarRecord.createdAt.max()),
				bloodSugarRecord.level.avg()))
			.from(bloodSugarRecord)
			.where(bloodSugarRecord.createdAt
				.between(endDate.minusMonths(size).withDayOfMonth(1).atStartOfDay(),
					endDate.atTime(23, 59, 59)))
			.groupBy(bloodSugarRecord.createdAt.month())
			.fetch();
	}

	private List<FindPeriodicBloodSugar> findWeeklyRecordByDate(LocalDate endDate, Long size) {
		return jpaQueryFactory.select(new QFindPeriodicBloodSugar(
				getDateFromDateTime(bloodSugarRecord.createdAt.max()),
				bloodSugarRecord.level.avg()))
			.from(bloodSugarRecord)
			.where(bloodSugarRecord.createdAt
				.between(endDate.minusWeeks(size).atStartOfDay(),
					endDate.atTime(23, 59, 59)))
			.groupBy(bloodSugarRecord.createdAt.week())
			.fetch();
	}

	private List<FindPeriodicBloodSugar> findDailyRecordByDate(LocalDate endDate, Long size) {
		return jpaQueryFactory.select(new QFindPeriodicBloodSugar(
				getDateFromDateTime(bloodSugarRecord.createdAt),
				bloodSugarRecord.level.avg()))
			.from(bloodSugarRecord)
			.where(bloodSugarRecord.createdAt
				.between(endDate.minusDays(size).atStartOfDay(), endDate.atTime(23, 59, 59)))
			.groupBy(bloodSugarRecord.createdAt)
			.fetch();
	}

	private boolean hasNextPage(List<FindPeriodicBloodSugar> data, int pageSize) {
		if (data.size() > pageSize) {
			data.remove(pageSize);
			return true;
		}
		return false;
	}
}
