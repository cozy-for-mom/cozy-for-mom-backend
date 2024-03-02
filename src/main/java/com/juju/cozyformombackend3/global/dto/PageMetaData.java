package com.juju.cozyformombackend3.global.dto;

import org.springframework.data.domain.Slice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PageMetaData {
	private final int pageNumber;
	private final int pageSize;
	private final long offset;
	private final boolean first;
	private final boolean last;
	private final int size;

	private PageMetaData(Slice slice) {
		this.pageNumber = slice.getPageable().getPageNumber();
		this.pageSize = slice.getPageable().getPageSize();
		this.offset = slice.getPageable().getOffset();
		this.first = slice.isFirst();
		this.last = slice.isLast();
		this.size = slice.getSize();
	}

	public static PageMetaData of(Slice slice) {
		return new PageMetaData(slice);
	}
}

/**
 *       "pageable": {
 *         "pageNumber": 0,
 *         "pageSize": 1,
 *         "sort": {
 *           "empty": true,
 *         },
 *         "offset": 0,
 *       },
 *       "first": true,
 *       "last": true,
 *       "size": 1,
 *
 */
