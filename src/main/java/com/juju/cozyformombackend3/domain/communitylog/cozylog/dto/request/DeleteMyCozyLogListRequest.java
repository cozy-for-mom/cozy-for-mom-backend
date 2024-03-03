package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class DeleteMyCozyLogListRequest {
	private List<Long> cozyLogIds;
}
