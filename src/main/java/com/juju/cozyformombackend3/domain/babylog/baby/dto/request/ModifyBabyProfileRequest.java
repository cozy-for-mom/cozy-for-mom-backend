package com.juju.cozyformombackend3.domain.babylog.baby.dto.request;

import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.juju.cozyformombackend3.domain.babylog.baby.error.BabyErrorCode;
import com.juju.cozyformombackend3.domain.babylog.baby.model.Gender;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;
import com.juju.cozyformombackend3.global.validation.annotation.IsGenderType;
import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDate;
import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresentDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ModifyBabyProfileRequest {

    @IsLocalDate
    @IsPastOrPresentDate
    private String dueAt;
    private String profileImageUrl;
    private List<BabyDto> babies;

    @Getter
    @RequiredArgsConstructor
    public static class BabyDto {
        @Min(1)
        private final Long babyId;
        @NotBlank(message = "아이 이름을 입력해주세요.")
        private final String name;
        @IsGenderType
        private final String gender;

        //
        // public static BabyDto of(Long babyId, String name, String gender) {
        // 	return new BabyDto(babyId, name, gender);
        // }
        public Gender getGender() {
            return Gender.ofType(gender);
        }
    }

    public LocalDate getDueAt() {
        return stringDateToLocalDate(this.dueAt);
    }

    public BabyDto getBaby(Long babyId) {
        return babies.stream()
            .filter(babyDto -> Objects.equals(babyDto.getBabyId(), babyId))
            .findFirst().orElseThrow(() -> new BusinessException(BabyErrorCode.NOT_FOUND_BABY));
    }

}
