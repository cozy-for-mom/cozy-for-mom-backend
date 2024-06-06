package com.juju.cozyformombackend3.domain.babylog.baby.controller.dto;

import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.juju.cozyformombackend3.domain.babylog.baby.error.BabyErrorCode;
import com.juju.cozyformombackend3.domain.babylog.baby.model.Gender;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;
import com.juju.cozyformombackend3.global.validation.annotation.IsFuture;
import com.juju.cozyformombackend3.global.validation.annotation.IsGenderType;
import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ModifyBabyProfile {

    @Getter
    public static class Request {

        @IsLocalDate
        @IsFuture
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

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final Long babyProfileId;
        private final List<Long> babyIds;

        public static Response of(final Long babyProfileId, List<Long> babyIds) {
            return new Response(babyProfileId, babyIds);
        }
    }
}