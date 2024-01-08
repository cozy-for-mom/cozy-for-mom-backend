package com.juju.cozyformombackend3.global.dto.response;

import lombok.Getter;

@Getter
public class CommonResponse<T> {

	private final T data;

	public CommonResponse(T data) {
		this.data = data;
	}

	public static <T> CommonResponse of(T data) {
		return new CommonResponse(data);
	}
}
