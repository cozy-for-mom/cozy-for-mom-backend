package com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.QFindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.QFindPeriodicBloodSugar;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.QBloodSugarRecord.bloodSugarRecord;

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
            case MONTHLY -> findMonthlyRecordByDate(condition.getUserId(), endDate, size);
            case WEEKLY -> findWeeklyRecordByDate(condition.getUserId(), endDate, size);
            default -> findDailyRecordByDate(condition.getUserId(), endDate, size);
        };
    }

    private List<FindPeriodicBloodSugar> findMonthlyRecordByDate(Long userId, LocalDate endDate, Long size) {
        return jpaQueryFactory.select(new QFindPeriodicBloodSugar(
                        bloodSugarRecord.recordAt.stringValue(),
                        bloodSugarRecord.level.avg()))
                .from(bloodSugarRecord)
                .where(bloodSugarRecord.user.id.eq(userId),
                        bloodSugarRecord.recordAt.between(endDate.minusMonths(size).withDayOfMonth(1), endDate))
                .groupBy(bloodSugarRecord.recordAt.month())
                .orderBy(bloodSugarRecord.recordAt.desc())
                .fetch();
    }

    private List<FindPeriodicBloodSugar> findWeeklyRecordByDate(Long userId, LocalDate endDate, Long size) {
        return jpaQueryFactory.select(new QFindPeriodicBloodSugar(
                        bloodSugarRecord.recordAt.stringValue(),
                        bloodSugarRecord.level.avg()))
                .from(bloodSugarRecord)
                .where(bloodSugarRecord.user.id.eq(userId),
                        bloodSugarRecord.recordAt.between(endDate.minusWeeks(size), endDate))
                .groupBy(bloodSugarRecord.recordAt.week())
                .orderBy(bloodSugarRecord.recordAt.desc())
                .fetch();
    }

    private List<FindPeriodicBloodSugar> findDailyRecordByDate(Long userId, LocalDate endDate, Long size) {
        return jpaQueryFactory.select(new QFindPeriodicBloodSugar(
                        bloodSugarRecord.recordAt.stringValue(),
                        bloodSugarRecord.level.avg()))
                .from(bloodSugarRecord)
                .where(bloodSugarRecord.user.id.eq(userId),
                        bloodSugarRecord.recordAt.between(endDate.minusDays(size), endDate))
                .groupBy(bloodSugarRecord.recordAt)
                .orderBy(bloodSugarRecord.recordAt.desc())
                .fetch();
    }
}
