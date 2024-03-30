package com.juju.cozyformombackend3.domain.userlog.weight.dto.request;

import java.time.LocalDate;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateWeightRequest {

    @NotNull(message = "몸무게를 입력해주세요.")
    private double weight;

    public WeightRecord toWeightRecord(LocalDate date, User user) {
        return WeightRecord.builder()
            .recordAt(date)
            .user(user)
            .weight(weight)
            .build();
    }
}
