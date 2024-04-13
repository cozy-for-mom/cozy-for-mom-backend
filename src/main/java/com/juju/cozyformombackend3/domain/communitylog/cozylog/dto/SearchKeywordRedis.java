package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchKeywordRedis {
    private String keyword;

    private String createdAt;
}