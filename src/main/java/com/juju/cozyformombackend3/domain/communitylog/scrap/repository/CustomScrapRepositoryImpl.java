package com.juju.cozyformombackend3.domain.communitylog.scrap.repository;

import static com.juju.cozyformombackend3.domain.communitylog.scrap.model.QScrap.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomScrapRepositoryImpl implements CustomScrapRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public void deleteScrapByUserIdAndCozyLogIds(Long userId, List<Long> cozyLogIds) {
		queryFactory.delete(scrap)
			.where(scrap.user.userId.eq(userId).and(scrap.cozyLogId.in(cozyLogIds)))
			.execute();
	}
}
