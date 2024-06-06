package com.juju.cozyformombackend3.domain.userlog.bloodsugar.controller.dto;

import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindDaliyBloodSugar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindDailyBloodSugarListResponse {
    List<FindDaliyBloodSugar> bloodSugars;

    public static FindDailyBloodSugarListResponse of(List<FindDaliyBloodSugar> bloodSugars) {
        return new FindDailyBloodSugarListResponse(bloodSugars);
    }
}
