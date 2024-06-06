package com.juju.cozyformombackend3.domain.userlog.bloodsugar.controller.dto;

import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindBloodSugarListResponse {
    private final String type;
    private final List<FindPeriodicBloodSugar> bloodsugars;

    public static FindBloodSugarListResponse of(String type, List<FindPeriodicBloodSugar> bloodsugars) {
        return new FindBloodSugarListResponse(type, bloodsugars);
    }
}
