package com.juju.cozyformombackend3.domain.babylog.growth.repository;

import static com.juju.cozyformombackend3.domain.babylog.growth.model.QGrowthDiary.*;
import static com.juju.cozyformombackend3.domain.babylog.growth.model.QGrowthReport.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.GrowthSummary;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.QGrowthSummary;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomGrowthReportRepositoryImpl implements CustomGrowthReportRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<GrowthSummary> findGrowthSummaryListByLastIdAndSize(Long reportId, Long size) {
		return jpaQueryFactory.select(new QGrowthSummary(growthReport.id, growthReport.recordAt,
				growthDiary.imageUrl, growthDiary.title, growthDiary.content))
			.from(growthReport)
			.leftJoin(growthReport.growthDiary, growthDiary)
			.where(ltReportId(reportId))
			.orderBy(growthReport.recordAt.desc())
			.limit(size)
			.fetch();
	}

	private BooleanExpression ltReportId(Long reportId) {
		if (reportId == 0) {
			return null;
		}
		return growthReport.id.lt(reportId);
	}
}
