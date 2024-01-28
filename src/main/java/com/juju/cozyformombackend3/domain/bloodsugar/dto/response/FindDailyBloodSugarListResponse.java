package com.juju.cozyformombackend3.domain.bloodsugar.dto.response;

import com.juju.cozyformombackend3.domain.bloodsugar.dto.object.FindDaliyBloodSugar;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FindDailyBloodSugarListResponse {
    List<FindDaliyBloodSugar> bloodSugars;

    public static FindDailyBloodSugarListResponse of(List<FindDaliyBloodSugar> bloodSugars) {
        return new FindDailyBloodSugarListResponse(bloodSugars);
    }
}
