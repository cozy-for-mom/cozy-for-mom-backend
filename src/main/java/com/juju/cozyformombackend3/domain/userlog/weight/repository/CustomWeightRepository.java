package com.juju.cozyformombackend3.domain.userlog.weight.repository;

import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.weight.dto.object.FindPeriodicWeight;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;

public interface CustomWeightRepository {
	List<FindPeriodicWeight> findPeriodRecordByDate(FindPeriodRecordCondition condition);
}
