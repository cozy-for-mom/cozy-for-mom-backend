package com.juju.cozyformombackend3.domain.userlog.supplement.dto.response;

import com.juju.cozyformombackend3.domain.userlog.supplement.dto.object.FindDailySupplementIntake;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.juju.cozyformombackend3.global.util.DateParser.dateTimeToString;

@Getter
@AllArgsConstructor
public class GetDailySupplementResponse {
	private final List<DailySupplement> supplements;

	public static GetDailySupplementResponse of(List<FindDailySupplementIntake> findRecordList) {
		//        List<DailySupplement> supplements = new ArrayList<>();
		//        for (FindDailySupplementIntake findRecord : findRecordList) {
		//            boolean isExist = false;
		//            if (supplements != null) {
		//                for (DailySupplement s : supplements) {
		//                    if (s.getSupplementId().equals(findRecord.getSupplementId())) {
		//                        s.getDatetimes().add(dateTimeToString(findRecord.getDatetime()));
		//                        s.setRealCount(s.getRealCount() + 1);
		//                        isExist = true;
		//                        break;
		//                    }
		//                }
		//            }
		//            if (isExist == false) {
		//                supplements.add(DailySupplement.builder()
		//                        .supplementId(findRecord.getSupplementId())
		//                        .supplementName(findRecord.getSupplementName())
		//                        .targetCount(findRecord.getTargetCount())
		//                        .realCount(1)
		//                        .datetimes(List.of(dateTimeToString(findRecord.getDatetime())))
		//                        .build());
		//            }
		//        }
		//        return new GetDailySupplementResponse(supplements);
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
					ds.datetimes.add(dateTimeToString(intake.getDatetime()));
					return ds;
				},
				(s1, s2) -> {
					System.out.println(s1.getDatetimes().get(0));
					s1.getDatetimes().addAll(s2.getDatetimes());
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
		private final List<String> datetimes = new ArrayList<>();
		private int realCount;

		private void setRealCount(int realCount) {
			this.realCount = realCount;
		}
	}
}
