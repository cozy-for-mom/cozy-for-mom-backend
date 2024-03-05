package com.juju.cozyformombackend3.domain.userlog.weight.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;

public interface WeightRepository extends JpaRepository<WeightRecord, Long>, CustomWeightRepository {

	boolean existsByUserAndRecordAt(User user, LocalDate date);

	Optional<WeightRecord> findByUserAndRecordAt(User user, LocalDate date);
}
