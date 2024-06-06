package com.juju.cozyformombackend3.domain.babylog.baby.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class RemoveBabyProfile {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final Long babyProfileId;
        private final List<Long> babyIds;

        public static Response of(final Long babyProfileId, List<Long> babyIds) {
            return new Response(babyProfileId, babyIds);
        }
    }
}
