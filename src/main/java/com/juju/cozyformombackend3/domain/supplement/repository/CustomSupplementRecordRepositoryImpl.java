package com.juju.cozyformombackend3.domain.supplement.repository;

import com.juju.cozyformombackend3.domain.supplement.dto.object.FindDailySupplementIntake;
import com.juju.cozyformombackend3.domain.supplement.dto.object.QFindDailySupplementIntake;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.juju.cozyformombackend3.domain.supplement.model.QSupplementRecord.supplementRecord;
import static com.juju.cozyformombackend3.global.repository.DateParser.getDateFromDateTime;

@RequiredArgsConstructor
public class CustomSupplementRecordRepositoryImpl implements CustomSupplementRecordRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FindDailySupplementIntake> findDailySupplementIntake(long userId, String date) {
        return queryFactory.select(new QFindDailySupplementIntake(
                        supplementRecord.supplement.supplementId,
                        supplementRecord.supplement.supplementName,
                        supplementRecord.supplement.targetCount,
                        supplementRecord.createdAt))
                .from(supplementRecord)
                .where(supplementRecord.supplement.user.userId.eq(userId)
                        .and(getDateFromDateTime(supplementRecord.createdAt).eq(date)))
                .fetch();
    }
}
