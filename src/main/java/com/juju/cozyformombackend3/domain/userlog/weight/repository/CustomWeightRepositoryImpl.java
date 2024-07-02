package com.juju.cozyformombackend3.domain.userlog.weight.repository;

import com.juju.cozyformombackend3.domain.userlog.weight.dto.object.FindPeriodicWeight;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.object.QFindPeriodicWeight;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.juju.cozyformombackend3.domain.userlog.weight.model.QWeightRecord.weightRecord;

@RequiredArgsConstructor
public class CustomWeightRepositoryImpl implements CustomWeightRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FindPeriodicWeight> findPeriodRecordByDate(FindPeriodRecordCondition condition) {
        return switch (condition.getType()) {
            case MONTHLY -> findMonthlyRecordByDate(condition.getUserId(), condition.getDate(), condition.getSize());
            case WEEKLY -> findWeeklyRecordByDate(condition.getUserId(), condition.getDate(), condition.getSize());
            default -> findDailyRecordByDate(condition.getUserId(), condition.getDate(), condition.getSize());
        };
    }

    private List<FindPeriodicWeight> findMonthlyRecordByDate(Long userId, LocalDate endDate, Long size) {
        return jpaQueryFactory.select(new QFindPeriodicWeight(
                        weightRecord.recordAt.max(),
                        weightRecord.weight.avg()))
                .from(weightRecord)
                .where(weightRecord.user.id.eq(userId),
                        weightRecord.recordAt.between(endDate.minusMonths(size).withDayOfMonth(1), endDate))
                .groupBy(weightRecord.recordAt.month())
                .orderBy(weightRecord.recordAt.month().desc())
                .fetch();
    }

    private List<FindPeriodicWeight> findWeeklyRecordByDate(Long userId, LocalDate endDate, Long size) {
        return jpaQueryFactory.select(new QFindPeriodicWeight(
                        weightRecord.recordAt.max(),
                        weightRecord.weight.avg()))
                .from(weightRecord)
                .where(weightRecord.user.id.eq(userId),
                        weightRecord.recordAt.between(endDate.minusWeeks(size), endDate))
                .groupBy(weightRecord.recordAt.week())
                .orderBy(weightRecord.recordAt.week().desc())
                .fetch();
    }

    private List<FindPeriodicWeight> findDailyRecordByDate(Long userId, LocalDate endDate, Long size) {
        return jpaQueryFactory.select(new QFindPeriodicWeight(
                        weightRecord.recordAt,
                        weightRecord.weight))
                .from(weightRecord)
                .where(weightRecord.user.id.eq(userId),
                        weightRecord.recordAt.between(endDate.minusDays(size), endDate))
                .orderBy(weightRecord.recordAt.desc())
                .fetch();
    }
}
