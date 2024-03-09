package com.juju.cozyformombackend3.domain.userlog.supplement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateSupplementRequest {

    @NotBlank(message = "수정하려는 영양제의 이름을 입력해주세요.")
    private String supplementName;

    @Min(1)
    private int targetCount;
}
