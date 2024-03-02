package com.juju.cozyformombackend3.domain.userlog.weight.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;

public interface WeightRepository extends JpaRepository<WeightRecord, Long>, CustomWeightRepository {

	boolean existsByUserAndRecordDate(User user, LocalDate date);

	Optional<WeightRecord> findByUserAndRecordDate(User user, LocalDate date);
}
