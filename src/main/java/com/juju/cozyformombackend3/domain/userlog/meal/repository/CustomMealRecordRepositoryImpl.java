package com.juju.cozyformombackend3.domain.userlog.meal.repository;

import com.juju.cozyformombackend3.domain.userlog.meal.dto.object.DailyMealRecord;
import com.juju.cozyformombackend3.domain.meal.dto.object.QDailyMealRecord;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.juju.cozyformombackend3.domain.meal.model.QMealRecord.mealRecord;
import static com.juju.cozyformombackend3.global.repository.DateParser.getDateFromDateTime;

@RequiredArgsConstructor
public class CustomMealRecordRepositoryImpl implements CustomMealRecordRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<DailyMealRecord> searchAllByUserIdAndDate(Long userId, String date) {
		return queryFactory.select(new QDailyMealRecord(mealRecord.mealId, mealRecord.recordAt,
				mealRecord.mealType, mealRecord.mealImageUrl))
			.from(mealRecord)
			.where(mealRecord.user.userId.eq(userId), getDateFromDateTime(mealRecord.recordAt).eq(date))
			.fetch();
	}
}
