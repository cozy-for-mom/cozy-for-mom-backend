package com.juju.cozyformombackend3.global.repository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.DatePath;
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

	public static Expression<String> getStringFromDatePath(DatePath<Date> datePath) {
		String formatPattern = "yyyy-MM-dd";

		return datePath.stringValue().concat(new SimpleDateFormat(formatPattern).format(datePath));
	}
}
