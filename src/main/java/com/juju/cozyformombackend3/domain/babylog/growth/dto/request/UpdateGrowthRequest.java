package com.juju.cozyformombackend3.domain.babylog.growth.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.error.BabyErrorCode;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;
import com.juju.cozyformombackend3.global.util.DateParser;
import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDate;
import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresentDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateGrowthRequest {

    @Min(1)
    private Long growthDiaryId;
    @Min(1)
    private Long babyProfileId;
    @IsLocalDate
    @IsPastOrPresentDate
    private String date;
    private String growthImageUrl;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String content;
    private List<BabyInfoRequest> babies;

    public LocalDate getRecordAt() {
        return DateParser.stringDateToLocalDate(date);
    }

    public BabyInfoRequest getBabyInfo(Long growthRecordId) {
        return babies.stream()
            .filter(babyInfo -> babyInfo.getGrowthRecordId().equals(growthRecordId))
            .findFirst()
            .orElseThrow(() -> new BusinessException(BabyErrorCode.NOT_FOUND_BABY));
    }

    public GrowthDiaryDto getGrowthDiaryDto() {
        return GrowthDiaryDto.of(growthDiaryId, babyProfileId, getRecordAt(), growthImageUrl, title, content);
    }

    @Getter
    public static class BabyInfoRequest {
        @Min(1)
        private Long growthRecordId;
        @Min(1)
        private Long babyId;
        private String babyName;
        private GrowthInfoRequest growthInfo;
    }

    @Getter
    public static class GrowthInfoRequest {

        private double weight;

        private double headDiameter;

        private double headCircum;

        private double abdomenCircum;

        private double thighLength;
    }

    @Getter
    @AllArgsConstructor(staticName = "of")
    // TODO: 이너클래스 공브하기
    public static class GrowthDiaryDto {

        private Long growthDiaryId;

        private Long babyProfileId;

        private LocalDate date;

        private String growthImageUrl;

        private String title;

        private String content;
    }
}
