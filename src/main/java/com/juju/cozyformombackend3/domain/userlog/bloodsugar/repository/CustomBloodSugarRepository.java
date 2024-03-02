package com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodBloodSugarCondition;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;

public interface CustomBloodSugarRepository {
	List<FindDaliyBloodSugar> searchAllByCreatedAt(Long userId, String createdAt);

	Slice<FindPeriodicBloodSugar> searchAllByPeriodType(long userId, String date, String type, Pageable pageable);

	Slice<FindPeriodicBloodSugar> searchAllByDailyType(long userId, LocalDate date, Pageable pageable);

	Slice<FindPeriodicBloodSugar> searchAllByWeeklyType(long userId, LocalDate date, Pageable pageable);

	List<FindPeriodicBloodSugar> findPeriodRecordByDate(FindPeriodBloodSugarCondition condition);
}
