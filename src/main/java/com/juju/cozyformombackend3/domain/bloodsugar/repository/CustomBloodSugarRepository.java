package com.juju.cozyformombackend3.domain.bloodsugar.repository;

import com.juju.cozyformombackend3.domain.bloodsugar.dto.object.FindDaliyBloodSugar;

import java.util.List;

public interface CustomBloodSugarRepository {
    List<FindDaliyBloodSugar> searchAllByCreatedAt(Long userId, String createdAt);
}
