package com.juju.cozyformombackend3.domain.babylog.growth.controller.dto;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.error.BabyErrorCode;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthReport;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateGrowth {

    @Getter
    @AllArgsConstructor
    public static class Request {
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

        private List<BabyInfo> babies;

        public LocalDate getRecordAt() {
            return DateParser.stringDateToLocalDate(date);
        }

        public BabyInfo getBabyInfo(Long growthRecordId) {
            return babies.stream()
                .filter(babyInfo -> babyInfo.getGrowthRecordId().equals(growthRecordId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(BabyErrorCode.NOT_FOUND_BABY));
        }

        public GrowthDiaryDto getGrowthDiaryDto() {
            return GrowthDiaryDto.of(growthDiaryId, babyProfileId, getRecordAt(), growthImageUrl,
                title, content);
        }

        @Getter
        public static class BabyInfo {
            @Min(1)
            private Long growthRecordId;
            @Min(1)
            private Long babyId;
            private GrowthInfo growthInfo;
        }

        @Getter
        public static class GrowthInfo {

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

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long growthReportId;
        private Long growthDiaryId;
        private List<Long> growthRecordIdList;

        public static UpdateGrowth.Response of(GrowthReport report) {
            return new Response(report.getId(), report.getGrowthDiary().getId(),
                report.getGrowthRecordList()
                    .stream()
                    .map(growthRecord -> growthRecord.getId())
                    .toList());
        }
    }
}
