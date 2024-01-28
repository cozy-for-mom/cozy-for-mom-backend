package com.juju.cozyformombackend3.global.repository;

import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;

import java.time.LocalDateTime;

public class DateParser {
    public static StringExpression getDateFromDateTime(DateTimePath<LocalDateTime> dateTime) {
        return Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", dateTime);
    }
}
