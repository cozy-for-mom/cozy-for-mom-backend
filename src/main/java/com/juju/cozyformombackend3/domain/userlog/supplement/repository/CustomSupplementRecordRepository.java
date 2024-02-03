package com.juju.cozyformombackend3.domain.userlog.supplement.repository;

import com.juju.cozyformombackend3.domain.userlog.supplement.dto.object.FindDailySupplementIntake;

import java.util.List;

public interface CustomSupplementRecordRepository {
	List<FindDailySupplementIntake> findDailySupplementIntake(long userId, String date);
}
