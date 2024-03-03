package com.juju.cozyformombackend3.domain.communitylog.cozylog.repository;

import static com.juju.cozyformombackend3.domain.communitylog.comment.model.QComment.*;
import static com.juju.cozyformombackend3.domain.communitylog.cozylog.model.QCozyLog.*;
import static com.juju.cozyformombackend3.domain.communitylog.cozylog.model.QCozyLogImage.*;
import static com.juju.cozyformombackend3.domain.communitylog.scrap.model.QScrap.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.QCozyLogSummary;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CozyLogSort;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomCozyLogRepositoryImpl implements CustomCozyLogRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<CozyLogSummary> findCozyLogListOrderBySort(CozyLogSort sort, Long reportId, Long size) {
		OrderSpecifier orderSpecifier = createOrderSpecifier(sort);

		SubQueryExpression<Long> firstImageSubQuery = JPAExpressions
			.select(cozyLogImage.id.min())
			.from(cozyLogImage)
			.where(cozyLogImage.cozyLog.id.eq(cozyLog.id));

		// List<CozyLogSummary> contents =
		return jpaQueryFactory
			.select(new QCozyLogSummary(
				cozyLog.id, cozyLog.title, cozyLog.content.substring(0, 40),
				cozyLog.createdAt, cozyLog.mode,
				comment.id.count(), scrap.id.count(),
				cozyLogImage.cozyLogImageUrl.coalesce(""), cozyLogImage.id.count()
			))
			.from(cozyLog)
			.leftJoin(comment).on(comment.cozyLog.id.eq(cozyLog.id))
			.leftJoin(scrap).on(scrap.cozyLogId.eq(cozyLog.id))
			.leftJoin(cozyLogImage).on(cozyLogImage.id.eq(firstImageSubQuery))
			// .leftJoin(cozyLogImage).on(cozyLogImage.cozyLog.id.eq(cozyLog.id))
			.where(isPublicCozyLog().and(cozyLog.id.lt(reportId)))
			.groupBy(cozyLog.id, cozyLog.title, cozyLog.content, cozyLog.createdAt, cozyLog.mode,
				cozyLogImage.cozyLogImageUrl)
			.orderBy(orderSpecifier, cozyLog.id.desc())
			.limit(size)
			.fetch();
	}

	private OrderSpecifier createOrderSpecifier(CozyLogSort sort) {
		return switch (sort) {
			// TODO: hot 기준 스크랩이랑 댓글 넣어서 수정하기
			case HOT -> new OrderSpecifier<>(Order.DESC, cozyLog.view);
			default -> new OrderSpecifier<>(Order.DESC, cozyLog.createdAt);
		};
	}

	private BooleanExpression isPublicCozyLog() {
		return cozyLog.mode.eq(CozyLogMode.PUBLIC);
	}

	private boolean hasNextPage(List<Object> contents, int pageSize) {
		if (contents.size() > pageSize) {
			contents.remove(pageSize);
			return true;
		}
		return false;
	}

}
