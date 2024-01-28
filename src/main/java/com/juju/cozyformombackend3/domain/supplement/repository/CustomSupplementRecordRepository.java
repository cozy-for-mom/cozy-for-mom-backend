package com.juju.cozyformombackend3.domain.supplement.repository;

import com.juju.cozyformombackend3.domain.supplement.dto.object.FindDailySupplementIntake;

import java.util.List;

public interface CustomSupplementRecordRepository {
    List<FindDailySupplementIntake> findDailySupplementIntake(long userId, String date);
}
