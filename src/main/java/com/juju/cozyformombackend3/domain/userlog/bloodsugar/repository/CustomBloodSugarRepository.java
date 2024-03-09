package com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;

public interface CustomBloodSugarRepository {
    List<FindPeriodicBloodSugar> findPeriodRecordByDate(FindPeriodRecordCondition condition);

    List<FindDaliyBloodSugar> searchAllByRecordAt(Long userId, LocalDate date);
}
