package com.juju.cozyformombackend3.domain.communitylog.scrap.repository;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;

public interface CustomScrapRepository {
	void deleteScrapByUserIdAndCozyLogIds(Long userId, List<Long> cozyLogIds);

	List<CozyLogSummary> findScrapListById(Long userId, Long reportId, Long size);
}