package com.juju.cozyformombackend3.domain.supplement.repository;

import com.juju.cozyformombackend3.domain.supplement.model.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {

	boolean existsBySupplementName(String supplementName);
}
