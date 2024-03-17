package com.juju.cozyformombackend3.domain.communitylog.cozylog.repository;

import java.util.List;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.CozyLogCondition;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;

public interface CustomCozyLogRepository {
    List<CozyLogSummary> searchCozyLogListByCondition(CozyLogCondition condition);

    void deleteCozyLogByUserIdAndCozyLogIds(Long userId, List<Long> cozyLogIds);

    Long countByCondition(CozyLogCondition condition);
}
