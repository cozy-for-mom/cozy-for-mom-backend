package com.juju.cozyformombackend3.domain.userlog.meal.model;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum MealType {
	BREAKFAST(1, "아침"), LUNCH(2, "점심"), DINNER(3, "저녁"), SNACK(4, "간식");

	private final int code;

	private final String type;

	MealType(int code, String type) {
		this.code = code;
		this.type = type;
	}

	public static MealType ofCode(int code) {
		return Arrays.stream(MealType.values())
			.filter(e -> e.getCode() == code)
			.findAny()
			.orElseThrow(() -> new NoSuchElementException("이런 코드의 음식 없음"));
		// TODO: 예외구문 넣기
	}

	public static MealType ofType(String type) {
		return Arrays.stream(MealType.values())
			.filter(e -> e.getType().equals(type))
			.findAny()
			.orElseThrow(() -> new NoSuchElementException("이런 타입의 음식 없음"));
		// TODO: 예외구문 넣기
	}

	public int getCode() {
		return this.code;
	}

	public String getType() {
		return this.type;
	}
}

