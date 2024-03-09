package com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request;

import java.time.LocalDate;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecordType;
import com.juju.cozyformombackend3.global.validation.annotation.IsBloodSugarRecordType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveBloodSugarRecordRequest {

    @NotNull(message = "혈당을 기록할 날짜를 입력해주세요.")
    @PastOrPresent(message = "현재까지의 날짜에 기록할 수 있습니다.")
    private LocalDate date;

    @NotNull(message = "혈당 기록 타입을 입력해주세요.")
    @IsBloodSugarRecordType
    private String type;

    @NotNull(message = "혈당 수치를 입력해주세요.")
    @Min(0)
    private int level;

    public BloodSugarRecordType getType() {
        return BloodSugarRecordType.ofDescription(type);
    }

    public BloodSugarRecord toBloodSugar(User user) {
        return BloodSugarRecord.builder()
            .user(user)
            .recordAt(date)
            .bloodSugarRecordType(BloodSugarRecordType.ofDescription(type))
            .level(level)
            .build();
    }
}
