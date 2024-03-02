package com.juju.cozyformombackend3.domain.babylog.growth.repository;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.GrowthSummary;

public interface CustomGrowthReportRepository {
	List<GrowthSummary> findGrowthSummaryListByLastIdAndSize(Long reportId, Long size);
}
