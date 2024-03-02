package com.juju.cozyformombackend3.domain.communitylog.scrap.repository;

import java.util.List;

public interface CustomScrapRepository {
	void deleteScrapByUserIdAndCozyLogIds(Long userId, List<Long> cozyLogIds);
}