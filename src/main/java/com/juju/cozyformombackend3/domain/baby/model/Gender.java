package com.juju.cozyformombackend3.domain.baby.model;

import com.juju.cozyformombackend3.domain.meal.model.MealType;
import java.util.Arrays;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
	FEMALE("여자"), MALE("남자");

	private final String type;

	public static MealType ofType(String type) {
		return Arrays.stream(MealType.values())
						.filter(e -> e.getType().equals(type))
						.findAny()
						.orElseThrow(() -> new NoSuchElementException("이런 타입의 음식 없음"));
		// TODO: 예외구문 넣기
	}

	public String getType() {
		return this.type;
	}
}
