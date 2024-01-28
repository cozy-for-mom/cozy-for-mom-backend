package com.juju.cozyformombackend3.domain.bloodsugar.repository;

import com.juju.cozyformombackend3.domain.bloodsugar.dto.object.FindDaliyBloodSugar;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.object.FindPeriodicBloodSugar;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.List;

public interface CustomBloodSugarRepository {
    List<FindDaliyBloodSugar> searchAllByCreatedAt(Long userId, String createdAt);

    Slice<FindPeriodicBloodSugar> searchAllByPeriodType(long userId, String date, String type, Pageable pageable);

    Slice<FindPeriodicBloodSugar> searchAllByDailyType(long userId, LocalDate date, Pageable pageable);

    Slice<FindPeriodicBloodSugar> searchAllByWeeklyType(long userId, LocalDate date, Pageable pageable);
}
