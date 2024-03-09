package com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository;

import static com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.QBloodSugarRecord.*;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.QFindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.QFindPeriodicBloodSugar;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomBloodSugarRepositoryImpl implements CustomBloodSugarRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FindDaliyBloodSugar> searchAllByRecordAt(Long userId, LocalDate recordAt) {
        return jpaQueryFactory
            .select(new QFindDaliyBloodSugar(bloodSugarRecord.id, bloodSugarRecord.bloodSugarRecordType,
                bloodSugarRecord.level))
            .from(bloodSugarRecord)
            .where(bloodSugarRecord.user.id.eq(userId),
                bloodSugarRecord.recordAt.eq(recordAt))
            .orderBy(bloodSugarRecord.recordAt.asc())
            .fetch();
    }

    @Override
    public List<FindPeriodicBloodSugar> findPeriodRecordByDate(FindPeriodRecordCondition condition) {
        LocalDate endDate = condition.getDate();
        Long size = condition.getSize();

        return switch (condition.getType()) {
            case MONTHLY -> findMonthlyRecordByDate(endDate, size);
            case WEEKLY -> findWeeklyRecordByDate(endDate, size);
            default -> findDailyRecordByDate(endDate, size);
        };
    }

    private List<FindPeriodicBloodSugar> findMonthlyRecordByDate(LocalDate endDate, Long size) {
        return jpaQueryFactory.select(new QFindPeriodicBloodSugar(
                bloodSugarRecord.recordAt.stringValue(),
                bloodSugarRecord.level.avg()))
            .from(bloodSugarRecord)
            .where(bloodSugarRecord.recordAt
                .between(endDate.minusMonths(size).withDayOfMonth(1), endDate))
            .groupBy(bloodSugarRecord.recordAt.month())
            .fetch();
    }

    private List<FindPeriodicBloodSugar> findWeeklyRecordByDate(LocalDate endDate, Long size) {
        return jpaQueryFactory.select(new QFindPeriodicBloodSugar(
                bloodSugarRecord.recordAt.stringValue(),
                bloodSugarRecord.level.avg()))
            .from(bloodSugarRecord)
            .where(bloodSugarRecord.recordAt
                .between(endDate.minusWeeks(size), endDate))
            .groupBy(bloodSugarRecord.recordAt.week())
            .fetch();
    }

    private List<FindPeriodicBloodSugar> findDailyRecordByDate(LocalDate endDate, Long size) {
        return jpaQueryFactory.select(new QFindPeriodicBloodSugar(
                bloodSugarRecord.recordAt.stringValue(),
                bloodSugarRecord.level.avg()))
            .from(bloodSugarRecord)
            .where(bloodSugarRecord.recordAt
                .between(endDate.minusDays(size), endDate))
            .groupBy(bloodSugarRecord.recordAt)
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
