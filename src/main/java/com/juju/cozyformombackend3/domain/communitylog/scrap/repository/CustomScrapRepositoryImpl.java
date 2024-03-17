package com.juju.cozyformombackend3.domain.communitylog.scrap.repository;

import static com.juju.cozyformombackend3.domain.communitylog.comment.model.QComment.*;
import static com.juju.cozyformombackend3.domain.communitylog.cozylog.model.QCozyLog.*;
import static com.juju.cozyformombackend3.domain.communitylog.cozylog.model.QCozyLogImage.*;
import static com.juju.cozyformombackend3.domain.communitylog.scrap.model.QScrap.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.QCozyLogSummary;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomScrapRepositoryImpl implements CustomScrapRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteScrapByUserIdAndCozyLogIds(Long userId, List<Long> cozyLogIds) {
        jpaQueryFactory.delete(scrap)
            .where(scrap.user.id.eq(userId).and(scrap.cozyLogId.in(cozyLogIds)))
            .execute();
    }

    @Override
    public List<CozyLogSummary> findScrapListById(Long userId, Long reportId, Long size) {
        SubQueryExpression<Long> firstImageSubQuery = JPAExpressions
            .select(cozyLogImage.id.min())
            .from(cozyLogImage)
            .where(cozyLogImage.cozyLog.id.eq(cozyLog.id));

        return jpaQueryFactory.select(new QCozyLogSummary(
                cozyLog.id, cozyLog.title, cozyLog.content.substring(0, 40),
                cozyLog.createdAt, cozyLog.mode,
                comment.id.count(), scrap.id.count(),
                cozyLogImage.imageUrl.coalesce(""), cozyLog.cozyLogImageList.size()))
            .from(scrap)
            .leftJoin(cozyLog).on(cozyLog.id.eq(scrap.cozyLogId))
            .leftJoin(comment).on(comment.cozyLog.id.eq(cozyLog.id))
            .leftJoin(cozyLogImage).on(cozyLogImage.id.eq(firstImageSubQuery))
            .where(scrap.user.id.eq(userId).and(ltReportId(reportId)))
            .groupBy(cozyLog.id, cozyLog.title, cozyLog.content, cozyLog.createdAt, cozyLog.mode,
                cozyLogImage.imageUrl, scrap.createdAt)
            .orderBy(scrap.createdAt.desc())
            .limit(size)
            .fetch();
    }

    private BooleanExpression ltReportId(Long reportId) {
        if (reportId == 0) {
            return null;
        }
        return scrap.id.lt(reportId);
    }
}
