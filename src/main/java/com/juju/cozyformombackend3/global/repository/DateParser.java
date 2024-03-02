package com.juju.cozyformombackend3.global.repository;

import java.time.LocalDateTime;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;

public class DateParser {
	public static StringExpression getDateFromDateTime(DateTimePath<LocalDateTime> dateTime) {
		return Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", dateTime);
	}

	public static StringExpression getDateFromDateTime(DateTimeExpression<LocalDateTime> dateTime) {
		return Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", dateTime);
	}
}
