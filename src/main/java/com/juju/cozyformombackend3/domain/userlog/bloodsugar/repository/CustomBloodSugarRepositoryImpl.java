package com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository;

import static com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.QBloodSugarRecord.*;
import static com.juju.cozyformombackend3.global.repository.DateParser.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodBloodSugarCondition;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.QFindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.QFindPeriodicBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecord;
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
	public Slice<FindPeriodicBloodSugar> searchAllByPeriodType(long userId, String date, String type,
		Pageable pageable) {
		List<BloodSugarRecord> data = jpaQueryFactory
			.selectFrom(bloodSugarRecord)
			.where(bloodSugarRecord.user.userId.eq(userId),
				getDateFromDateTime(bloodSugarRecord.createdAt).eq(date)//,
				//                        bloodSugarRecord.bloodSugarRecordType.eq(BloodSugarRecord.BloodSugarRecordType.valueOf(type))
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.orderBy(bloodSugarRecord.createdAt.desc())
			.fetch();

		//        return new SliceImpl(data, pageable, hasNextPage(data, pageable.getPageSize()));
		return null;
	}

	@Override
	public Slice<FindPeriodicBloodSugar> searchAllByDailyType(long userId, LocalDate date, Pageable pageable) {
		List<LocalDateTime> datesBeforeGivenDate = jpaQueryFactory
			.selectDistinct(bloodSugarRecord.createdAt)
			.from(bloodSugarRecord)
			.where(bloodSugarRecord.user.userId.eq(userId)
				.and(bloodSugarRecord.createdAt.lt(LocalDateTime.of(date, LocalDateTime.MAX.toLocalTime())))
				.and(bloodSugarRecord.createdAt.gt(
					LocalDateTime.of(date.minusDays(pageable.getPageSize()), LocalDateTime.MIN.toLocalTime()))))
			.orderBy(bloodSugarRecord.createdAt.desc())
			.fetch();

		List<FindPeriodicBloodSugar> data = jpaQueryFactory
			.select(new QFindPeriodicBloodSugar(getDateFromDateTime(bloodSugarRecord.createdAt),
				bloodSugarRecord.level.avg()))
			.from(bloodSugarRecord)
			.where(bloodSugarRecord.user.userId.eq(userId)
				.and(bloodSugarRecord.createdAt.in(datesBeforeGivenDate)))
			.groupBy(getDateFromDateTime(bloodSugarRecord.createdAt))
			.orderBy(getDateFromDateTime(bloodSugarRecord.createdAt).desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return new SliceImpl(data, pageable, hasNextPage(data, pageable.getPageSize()));
	}

	@Override
	public Slice<FindPeriodicBloodSugar> searchAllByWeeklyType(long userId, LocalDate date, Pageable pageable) {
		LocalDate startDate = date.minusDays(6); // 시작일로부터 6일 전까지

		List<FindPeriodicBloodSugar> averages = jpaQueryFactory
			.select(bloodSugarRecord.createdAt.week(),
				bloodSugarRecord.createdAt.year(),
				bloodSugarRecord.level.avg())
			.from(bloodSugarRecord)
			.where(bloodSugarRecord.createdAt.between(startDate.atStartOfDay(),
					date.atTime(LocalDateTime.MAX.toLocalTime())),
				bloodSugarRecord.user.userId.eq(userId))
			.groupBy(bloodSugarRecord.createdAt.week(), bloodSugarRecord.createdAt.year())
			.orderBy(bloodSugarRecord.createdAt.week().desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch()
			.stream()
			.map(tuple -> new FindPeriodicBloodSugar(
				String.valueOf(LocalDate.of(tuple.get(bloodSugarRecord.createdAt.year()),
					1, 1).plusWeeks(tuple.get(bloodSugarRecord.createdAt.week()))),
				tuple.get(bloodSugarRecord.level.avg())))
			.collect(Collectors.toList());

		return new SliceImpl<>(averages, pageable, hasNextPage(averages, pageable.getPageSize()));
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
