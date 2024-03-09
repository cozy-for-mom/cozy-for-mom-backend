package com.juju.cozyformombackend3.domain.userlog.weight.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateWeightResponse {

    private Long weightRecordId;

    public static UpdateWeightResponse of(Long weightRecordId) {
        return new UpdateWeightResponse(weightRecordId);
    }
}