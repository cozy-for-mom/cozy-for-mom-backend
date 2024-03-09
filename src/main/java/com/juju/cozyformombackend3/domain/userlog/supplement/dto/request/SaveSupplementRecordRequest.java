package com.juju.cozyformombackend3.domain.userlog.supplement.dto.request;

import java.time.LocalDateTime;

import com.juju.cozyformombackend3.global.util.DateParser;
import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDateTime;
import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveSupplementRecordRequest {

    @NotBlank(message = "저장하려는 영양제의 이름을 입력해주세요.")
    private String supplementName;

    @NotNull(message = "영양제 섭취 날짜를 입력해주세요.")
    @IsLocalDateTime
    @IsPastOrPresent
    private String datetime;

    public LocalDateTime getRecordAt() {
        return DateParser.stringToLocalDateTime(datetime);
    }
}
