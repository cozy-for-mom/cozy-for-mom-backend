package com.juju.cozyformombackend3.domain.userlog.supplement.dto.response;

import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.juju.cozyformombackend3.domain.userlog.supplement.dto.object.FindDailySupplementIntake;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetDailySupplementResponse {
    private final List<DailySupplement> supplements;

    public static GetDailySupplementResponse of(List<FindDailySupplementIntake> findRecordList) {
        Map<Long, DailySupplement> supplementMap = findRecordList.stream()
            .collect(Collectors.toMap(
                FindDailySupplementIntake::getSupplementId,
                intake -> {
                    DailySupplement ds = DailySupplement.builder()
                        .supplementId(intake.getSupplementId())
                        .supplementName(intake.getSupplementName())
                        .targetCount(intake.getTargetCount())
                        .realCount(1)
                        .build();
                    ds.records.add(SupplementRecordDto.of(intake.getRecordId(), intake.getDatetime()));
                    return ds;
                },
                (s1, s2) -> {
                    s1.getRecords().addAll(s2.getRecords());
                    s1.setRealCount(s1.getRealCount() + s2.getRealCount());
                    return s1;
                }
            ));

        List<DailySupplement> supplements = new ArrayList<>(supplementMap.values());

        return new GetDailySupplementResponse(supplements);

    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static class DailySupplement {
        private final Long supplementId;
        private final String supplementName;
        private final int targetCount;
        private final List<SupplementRecordDto> records = new ArrayList<>();
        private int realCount;

        private void setRealCount(int realCount) {
            this.realCount = realCount;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static class SupplementRecordDto {
        private final Long id;
        private final String datetime;

        public static SupplementRecordDto of(Long id, LocalDateTime datetime) {
            return new SupplementRecordDto(id, localDateTimeToStringFormatDateTime(datetime));
        }
    }
}
