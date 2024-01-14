package com.juju.cozyformombackend3.domain.growth.repository;

import com.juju.cozyformombackend3.domain.growth.model.GrowthDiary;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowthDiaryRepository extends JpaRepository<GrowthDiary, Long> {

	List<GrowthDiary> findAllByRecordAt(LocalDate date);
}
