package com.juju.cozyformombackend3.domain.communitylog.cozylog.repository;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.CozyLogSearchCondition;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CozyLogSort;
import com.juju.cozyformombackend3.global.dto.CustomSlice;

public interface CustomCozyLogRepository {
    List<CozyLogSummary> findCozyLogListOrderBySort(CozyLogSort keyword, Long reportId, Long size);

    List<CozyLogSummary> findCozyLogListByWriterId(Long userId, Long reportId, Long size);

    void deleteCozyLogByUserIdAndCozyLogIds(Long userId, List<Long> cozyLogIds);

    CustomSlice<CozyLogSummary> searchCozyLogByCondition(Long userId, CozyLogSearchCondition condition);
}
