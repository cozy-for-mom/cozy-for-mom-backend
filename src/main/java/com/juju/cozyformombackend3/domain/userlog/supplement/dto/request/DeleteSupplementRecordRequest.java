package com.juju.cozyformombackend3.domain.userlog.supplement.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteSupplementRecordRequest {

    @NotBlank(message = "삭제하려는 영양제의 이름을 입력해주세요.")
    private String supplementName;
    private List<LocalDateTime> datetimeList;
}
