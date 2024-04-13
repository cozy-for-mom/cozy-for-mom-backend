package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RecentSearchKeyword {

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final List<String> keyword;

        public static Response of(final List<String> keyword) {
            return new Response(keyword);
        }
    }
}
