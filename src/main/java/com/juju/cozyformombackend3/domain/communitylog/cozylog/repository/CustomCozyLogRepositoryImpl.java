package com.juju.cozyformombackend3.domain.communitylog.cozylog.repository;

import static com.juju.cozyformombackend3.domain.communitylog.comment.model.QComment.*;
import static com.juju.cozyformombackend3.domain.communitylog.cozylog.model.QCozyLog.*;
import static com.juju.cozyformombackend3.domain.communitylog.cozylog.model.QCozyLogImage.*;
import static com.juju.cozyformombackend3.domain.communitylog.scrap.model.QScrap.*;

import java.util.List;
import java.util.Objects;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.CozyLogSearchCondition;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.QCozyLogSummary;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CozyLogSort;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;
import com.juju.cozyformombackend3.global.dto.CustomSlice;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
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
                cozyLogImage.imageUrl.coalesce(""), cozyLogImage.id.count()
            ))
            .from(cozyLog)
            .leftJoin(comment).on(comment.cozyLog.id.eq(cozyLog.id))
            .leftJoin(scrap).on(scrap.cozyLogId.eq(cozyLog.id))
            .leftJoin(cozyLogImage).on(cozyLogImage.id.eq(firstImageSubQuery))
            .where(isPublicCozyLog().and(ltReportId(reportId)))
            .groupBy(cozyLog.id, cozyLog.title, cozyLog.content, cozyLog.createdAt, cozyLog.mode,
                cozyLogImage.imageUrl)
            .orderBy(createOrderSpecifier(sort), cozyLog.id.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<CozyLogSummary> findCozyLogListByWriterId(Long userId, Long reportId, Long size) {
        SubQueryExpression<Long> firstImageSubQuery = JPAExpressions
            .select(cozyLogImage.id.min())
            .from(cozyLogImage)
            .where(cozyLogImage.cozyLog.id.eq(cozyLog.id));

        return jpaQueryFactory.select(new QCozyLogSummary(
                cozyLog.id, cozyLog.title, cozyLog.content.substring(0, 40),
                cozyLog.createdAt, cozyLog.mode,
                comment.id.count(), scrap.id.count(),
                cozyLogImage.imageUrl.coalesce(""), cozyLogImage.id.count()
            ))
            .from(cozyLog)
            .leftJoin(comment).on(comment.cozyLog.id.eq(cozyLog.id))
            .leftJoin(scrap).on(scrap.cozyLogId.eq(cozyLog.id))
            .leftJoin(cozyLogImage).on(cozyLogImage.id.eq(firstImageSubQuery))
            .where(cozyLog.user.id.eq(userId).and(ltReportId(reportId)))
            .groupBy(cozyLog.id, cozyLog.title, cozyLog.content, cozyLog.createdAt, cozyLog.mode,
                cozyLogImage.imageUrl)
            .orderBy(cozyLog.createdAt.desc(), cozyLog.id.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public void deleteCozyLogByUserIdAndCozyLogIds(Long userId, List<Long> cozyLogIds) {
        jpaQueryFactory.delete(comment)
            .where(comment.cozyLog.id.in(cozyLogIds))
            .execute();

        jpaQueryFactory.delete(cozyLogImage)
            .where(cozyLogImage.cozyLog.id.in(cozyLogIds))
            .execute();

        jpaQueryFactory.delete(scrap)
            .where(scrap.cozyLogId.in(cozyLogIds))
            .execute();

        jpaQueryFactory.delete(cozyLog)
            .where(cozyLog.user.id.eq(userId).and(cozyLog.id.in(cozyLogIds)))
            .execute();
    }

    @Override
    public CustomSlice<CozyLogSummary> searchCozyLogByCondition(Long userId, CozyLogSearchCondition condition) {
        final SubQueryExpression<Long> firstImageSubQuery = JPAExpressions
            .select(cozyLogImage.id.min())
            .from(cozyLogImage)
            .where(cozyLogImage.cozyLog.id.eq(cozyLog.id));

        final List<CozyLogSummary> data = jpaQueryFactory
            .select(new QCozyLogSummary(
                cozyLog.id, cozyLog.title, cozyLog.content.substring(0, 40),
                cozyLog.createdAt, cozyLog.mode,
                comment.id.count(), scrap.id.count(),
                cozyLogImage.imageUrl.coalesce(""), cozyLogImage.id.count()
            ))
            .from(cozyLog)
            .leftJoin(comment).on(comment.cozyLog.id.eq(cozyLog.id))
            .leftJoin(scrap).on(scrap.cozyLogId.eq(cozyLog.id))
            .leftJoin(cozyLogImage).on(cozyLogImage.id.eq(firstImageSubQuery))
            .where(allowPrivateModeByUserId(userId)
                .and(searchByKeyword(condition.getKeyword()))
                .and(ltReportId(condition.getLastLogId())))
            .groupBy(cozyLog.id, cozyLog.title, cozyLog.content, cozyLog.createdAt, cozyLog.mode,
                cozyLogImage.imageUrl)
            .orderBy(createOrderSpecifier(condition.getSort()), cozyLog.id.desc())
            .limit(condition.getSize())
            .fetch();

        final Long totalCount = jpaQueryFactory
            .select(cozyLog.count())
            .from(cozyLog)
            .where(allowPrivateModeByUserId(userId)
                .and(searchByKeyword(condition.getKeyword()))
                .and(ltReportId(condition.getLastLogId())))
            .fetchOne();

        return CustomSlice.of(totalCount, data);
    }

    private Predicate searchByKeyword(String keyword) {
        return null;
    }

    private BooleanExpression allowPrivateModeByUserId(Long userId) {
        if (CozyLogMode.PRIVATE.equals(cozyLog.mode)) {
            return cozyLog.user.id.eq(userId);
        }
        return cozyLog.mode.eq(CozyLogMode.PUBLIC);
    }

    private BooleanExpression ltReportId(Long reportId) {
        if (Objects.isNull(reportId) || reportId == 0) {
            return null;
        }
        return cozyLog.id.lt(reportId);
    }

    private OrderSpecifier createOrderSpecifier(CozyLogSort sort) {
        return switch (sort) {
            // TODO: hot 기준 스크랩이랑 댓글 넣어서 수정하기
            case HOT -> new OrderSpecifier<>(Order.DESC, cozyLog.view);
            case COMMENT -> new OrderSpecifier<>(Order.DESC, cozyLog.commentList.size());
            default -> new OrderSpecifier<>(Order.DESC, cozyLog.createdAt);
        };
    }

    private BooleanExpression isPublicCozyLog() {
        return cozyLog.mode.eq(CozyLogMode.PUBLIC);
    }
}
