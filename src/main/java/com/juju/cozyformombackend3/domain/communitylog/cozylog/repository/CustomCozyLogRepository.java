package com.juju.cozyformombackend3.domain.communitylog.cozylog.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CozyLogSort;

public interface CustomCozyLogRepository {
	Slice<CozyLogSummary> findCozyLogListOrderBySort(CozyLogSort keyword, Pageable pageable);
}
