package com.juju.cozyformombackend3.domain.userlog.supplement.dto.response;

import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.juju.cozyformombackend3.domain.userlog.supplement.dto.object.FindDailySupplementIntake;
import com.juju.cozyformombackend3.domain.userlog.supplement.model.Supplement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetDailySupplementResponse {
    private final List<DailySupplement> supplements;

    public static GetDailySupplementResponse of(
        List<Supplement> findSupplementList,
        List<FindDailySupplementIntake> findRecordList) {

        final Map<Long, DailySupplement> supplementMap = findSupplementList.stream()
            .collect(Collectors.toMap(Supplement::getId,
                supplement -> DailySupplement.builder()
                    .supplementId(supplement.getId())
                    .supplementName(supplement.getName())
                    .targetCount(supplement.getTargetCount())
                    .realCount(0)
                    .build()));

        findRecordList.forEach(supplementRecord -> {
            DailySupplement saveRecord = supplementMap.get(supplementRecord.getSupplementId());
            saveRecord.getRecords()
                .add(SupplementRecordDto.of(supplementRecord.getRecordId(), supplementRecord.getDatetime()));
            saveRecord.realCount++;
        });
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
