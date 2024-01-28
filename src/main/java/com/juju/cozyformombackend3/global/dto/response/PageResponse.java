package com.juju.cozyformombackend3.global.dto.response;

import com.juju.cozyformombackend3.global.dto.PageMetaData;
import lombok.Getter;

@Getter
public class PageResponse<T> {
    private final T content;
    private final PageMetaData pageMetaData;

    private PageResponse(T content, PageMetaData pageMetaData) {
        this.content = content;
        this.pageMetaData = pageMetaData;
    }

    public static <T> PageResponse<T> of(T content, PageMetaData pageMetaData) {
        return new PageResponse<>(content, pageMetaData);
    }
}
