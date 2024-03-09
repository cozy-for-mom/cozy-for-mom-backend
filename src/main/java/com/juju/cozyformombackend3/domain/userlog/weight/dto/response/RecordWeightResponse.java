package com.juju.cozyformombackend3.domain.userlog.weight.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecordWeightResponse {

    private Long weightRecordId;

    public static RecordWeightResponse of(Long weightRecordId) {
        return new RecordWeightResponse(weightRecordId);
    }
}