package com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.Baby;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthRecord;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthReport;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindGrowthResponse {
    private final Long babyProfileId;
    private final String date;
    private final Long growthReportId;
    private final String growthImageUrl;
    private final Long growthDiaryId;
    private final String title;
    private final String content;
    private final List<BabyInfo> babies;

    public static FindGrowthResponse of(GrowthReport report) {
        List<Baby> babies = report.getBabyProfile().getBabyList();
        List<BabyInfo> babyInfoList = report.getGrowthRecordList().stream()
            .map(record -> BabyInfo.of(record, babies))
            .toList();

        return FindGrowthResponse.builder()
            .babyProfileId(report.getBabyProfile().getId())
            .date(report.getRecordAt().toString())
            .growthReportId(report.getId())
            .growthImageUrl(report.getGrowthDiary().getImageUrl())
            .growthDiaryId(report.getGrowthDiary().getId())
            .title(report.getGrowthDiary().getTitle())
            .content(report.getGrowthDiary().getContent())
            .babies(babyInfoList)
            .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static class BabyInfo {
        private final Long babyId;
        private final String babyName;
        private final GrowthInfo growthInfo;

        private static BabyInfo of(GrowthRecord record, List<Baby> babies) {
            Baby matchBaby = babies.stream()
                .filter(baby -> record.getBabyId().equals(baby.getId()))
                .findFirst()
                .orElseThrow();

            return BabyInfo.builder()
                .babyId(matchBaby.getId())
                .babyName(matchBaby.getName())
                .growthInfo(GrowthInfo.of(record))
                .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static class GrowthInfo {
        private final Long growthRecordId;
        private final double weight;
        private final double headDiameter;
        private final double headCircum;
        private final double abdomenCircum;
        private final double thighLength;

        private static GrowthInfo of(GrowthRecord record) {
            if (record == null) {
                return null;
            }
            return GrowthInfo.builder()
                .growthRecordId(record.getId())
                .weight(record.getWeight())
                .headDiameter(record.getHeadDiameter())
                .headCircum(record.getHeadCircum())
                .abdomenCircum(record.getAbdomenCircum())
                .thighLength(record.getThighLength())
                .build();
        }
    }
}
