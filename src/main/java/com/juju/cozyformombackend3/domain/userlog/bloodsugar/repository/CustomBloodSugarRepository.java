package com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository;

import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodBloodSugarCondition;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;

public interface CustomBloodSugarRepository {
	List<FindDaliyBloodSugar> searchAllByCreatedAt(Long userId, String createdAt);

	List<FindPeriodicBloodSugar> findPeriodRecordByDate(FindPeriodBloodSugarCondition condition);
}
