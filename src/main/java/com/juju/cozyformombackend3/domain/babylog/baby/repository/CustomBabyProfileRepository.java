package com.juju.cozyformombackend3.domain.babylog.baby.repository;

import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.FindGrowthDiaryRecord;

import java.time.LocalDate;

public interface CustomBabyProfileRepository {
	FindGrowthDiaryRecord searchGrowthRecord(Long userId, LocalDate date);
}
