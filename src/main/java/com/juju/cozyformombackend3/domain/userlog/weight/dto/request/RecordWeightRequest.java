package com.juju.cozyformombackend3.domain.userlog.weight.dto.request;

import java.time.LocalDate;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecordWeightRequest {

    @NotNull(message = "몸무게를 기록할 날짜를 입력해주세요.")
    @PastOrPresent(message = "현재까지의 날짜에 기록할 수 있습니다.")
    @Schema(description = "몸무게를 기록할 날짜", defaultValue = "2024-03-17")
    private LocalDate date;

    @NotNull(message = "몸무게를 입력해주세요.")
    @Schema(description = "몸무게", defaultValue = "33.33")
    private double weight;

    public WeightRecord toWeightRecord(User user) {
        return WeightRecord.builder()
            .recordAt(date)
            .user(user)
            .weight(weight)
            .build();
    }
}
