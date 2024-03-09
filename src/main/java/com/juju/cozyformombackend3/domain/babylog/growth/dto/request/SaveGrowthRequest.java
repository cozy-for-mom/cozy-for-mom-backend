package com.juju.cozyformombackend3.domain.babylog.growth.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.Baby;
import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.growth.error.GrowthErrorCode;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthDiary;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthRecord;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;
import com.juju.cozyformombackend3.global.util.DateParser;
import com.juju.cozyformombackend3.global.validation.annotation.IsLocalDate;
import com.juju.cozyformombackend3.global.validation.annotation.IsPastOrPresentDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveGrowthRequest {

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

    public GrowthDiary toGrowthDiary(BabyProfile babyProfile) {
        return GrowthDiary.of(babyProfile, getRecordAt(), growthImageUrl, title, content);
    }

    public GrowthRecord toGrowthRecord(Baby baby) {
        GrowthInfoRequest growthInfo = (babies.stream()
            .filter(babyInfo -> babyInfo.getBabyId().equals(baby.getId()))
            .findFirst()
            .orElseThrow(() -> new BusinessException(GrowthErrorCode.NOT_FOUND_MATCH_BABY_GROWTH_RECORD)))
            .getGrowthInfo();

        return GrowthRecord.of(baby, getRecordAt(), growthInfo.weight, growthInfo.headDiameter, growthInfo.headCircum,
            growthInfo.abdomenCircum, growthInfo.thighLength);
    }

    @Getter
    private static class BabyInfoRequest {
        @Min(1)
        private Long babyId;
        private String babyName;
        private GrowthInfoRequest growthInfo;
    }

    @Getter
    private static class GrowthInfoRequest {
        private double weight;
        private double headDiameter;
        private double headCircum;
        private double abdomenCircum;
        private double thighLength;
    }
}