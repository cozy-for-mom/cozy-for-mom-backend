package com.juju.cozyformombackend3.domain.supplement.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteSupplementRecordRequest {

	private String supplementName;
	private List<LocalDateTime> datetimeList;
}
