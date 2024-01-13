package com.juju.cozyformombackend3.domain.baby.repository;

import com.juju.cozyformombackend3.domain.baby.model.Baby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabyRepository extends JpaRepository<Baby, Long> {

}
