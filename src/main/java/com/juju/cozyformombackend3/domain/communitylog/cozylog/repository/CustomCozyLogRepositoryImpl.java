package com.juju.cozyformombackend3.domain.communitylog.cozylog.repository;

import static com.juju.cozyformombackend3.domain.communitylog.comment.model.QComment.*;
import static com.juju.cozyformombackend3.domain.communitylog.cozylog.model.QCozyLog.*;
import static com.juju.cozyformombackend3.domain.communitylog.cozylog.model.QCozyLogImage.*;
import static com.juju.cozyformombackend3.domain.communitylog.scrap.model.QScrap.*;
import static com.querydsl.core.types.dsl.Expressions.*;

import java.util.List;
import java.util.Objects;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.CozyLogCondition;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomCozyLogRepositoryImpl implements CustomCozyLogRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CozyLogSummary> searchCozyLogListByCondition(CozyLogCondition condition) {
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
            .where(allowPrivateModeByUserId(condition.getUserId())
                .and(filterLogByWriterId(condition.getWriterId()))
                .and(searchByKeyword(condition.getKeyword()))
                .and(ltReportId(condition.getLastLogId())))
            .groupBy(cozyLog.id, cozyLog.title, cozyLog.content, cozyLog.createdAt, cozyLog.mode,
                cozyLogImage.imageUrl)
            .orderBy(createOrderSpecifier(condition.getSort()), cozyLog.id.desc())
            .limit(condition.getSize())
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
    public Long countByCondition(CozyLogCondition condition) {
        return jpaQueryFactory.select(cozyLog.count())
            .from(cozyLog)
            .where(allowPrivateModeByUserId(condition.getUserId())
                .and(filterLogByWriterId(condition.getWriterId()))
                .and(searchByKeyword(condition.getKeyword())))
            .fetchOne();
    }

    private BooleanExpression filterLogByWriterId(Long writerId) {
        return Objects.nonNull(writerId) ?
            cozyLog.user.id.eq(writerId)
            : null;
    }

    private BooleanExpression searchByKeyword(String keyword) {
        if (Objects.isNull(keyword) || keyword.length() < 2) {
            return null;
        }
        final String formattedSearchWord = "\"+" + keyword + "*\"";

        return (numberTemplate(Double.class, "function('match_against', {0}, {1})",
            cozyLog.title, formattedSearchWord)
            .gt(0))
            .or(numberTemplate(Double.class, "function('match_against', {0}, {1})",
                cozyLog.content, formattedSearchWord)
                .gt(0)); //MATCH_THRESHOLD
    }

    private BooleanExpression allowPrivateModeByUserId(Long userId) {
        return cozyLog.mode.eq(CozyLogMode.PUBLIC)
            .or(cozyLog.mode.eq(CozyLogMode.PRIVATE).and(cozyLog.user.id.eq(userId)));
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
}
