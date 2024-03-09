package com.juju.cozyformombackend3.domain.userlog.meal.repository;

import static com.juju.cozyformombackend3.domain.userlog.meal.model.QMealRecord.*;
import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.meal.dto.object.DailyMealRecord;
import com.juju.cozyformombackend3.domain.userlog.meal.dto.object.QDailyMealRecord;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomMealRecordRepositoryImpl implements CustomMealRecordRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyMealRecord> searchAllByUserIdAndDate(Long userId, String date) {
        return queryFactory.select(new QDailyMealRecord(mealRecord.id, mealRecord.recordAt,
                mealRecord.mealType, mealRecord.imageUrl))
            .from(mealRecord)
            .where(mealRecord.user.id.eq(userId), getDateFromQueryLocalDateTime(mealRecord.recordAt).eq(date))
            .fetch();
    }
}
