package com.juju.cozyformombackend3.domain.communitylog.scrap.controller.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class UnscrapListRequest {
    private List<Long> cozyLogIds;
}
