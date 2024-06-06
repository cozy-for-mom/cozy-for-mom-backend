package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.dto;

import java.util.List;

import lombok.Getter;

public class DeleteMyCozyLogList {

    @Getter
    public static class Request {
        private List<Long> cozyLogIds;
    }
}

