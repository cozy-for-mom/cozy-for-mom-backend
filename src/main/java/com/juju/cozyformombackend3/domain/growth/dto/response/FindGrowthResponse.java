package com.juju.cozyformombackend3.domain.growth.dto.response;

import com.juju.cozyformombackend3.domain.baby.model.Baby;
import com.juju.cozyformombackend3.domain.growth.dto.object.FindGrowthDiaryRecord;
import com.juju.cozyformombackend3.domain.growth.model.GrowthRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindGrowthResponse {
    private final Long babyProfileId;
    private final String date;
    private final String growthImagePath;
    private final String content;
    private final List<BabyInfo> babies;

    public static FindGrowthResponse of(FindGrowthDiaryRecord record) {
        List<BabyInfo> babyInfoList = record.getBabies().stream()
                .map(baby -> BabyInfo.of(baby, record.getGrowthDiary().getRecordAt().toString()))
                .toList();

        return FindGrowthResponse.builder()
                .babyProfileId(record.getBabyProfileId())
                .date(record.getGrowthDiary().getRecordAt().toString())
                .growthImagePath(record.getGrowthDiary().getGrowthImageUrl())
                .content(record.getGrowthDiary().getContent())
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

        private static BabyInfo of(Baby baby, String date) {
            GrowthRecord growthRecord = baby.getGrowthRecordList().stream()
                    .filter(record -> record.getRecordAt().toString().equals(date))
                    .findFirst()
                    .orElse(null);

            return BabyInfo.builder()
                    .babyId(baby.getBabyId())
                    .babyName(baby.getName())
                    .growthInfo(GrowthInfo.of(growthRecord))
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
                    .growthRecordId(record.getGrowthRecordId())
                    .weight(record.getWeight())
                    .headDiameter(record.getHeadDiameter())
                    .headCircum(record.getHeadCircum())
                    .abdomenCircum(record.getAbdomenCircum())
                    .thighLength(record.getThighLength())
                    .build();
        }
    }
}
