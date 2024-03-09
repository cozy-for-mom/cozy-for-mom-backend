package com.juju.cozyformombackend3.global.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateParser {

    public static String localDateTimeToStringFormatDateTime(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String localDateTimeToStringFormatDate(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String localDateToStringFormatDate(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDateTime stringDateTimeToLocalDateTime(String datetime) {
        String formatPattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

        return LocalDateTime.parse(datetime, formatter);
    }

    public static LocalDate stringDateToLocalDate(String date) {
        String formatPattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

        return LocalDate.parse(date, formatter);
    }

    public static StringExpression getDateFromQueryLocalDate(DateTimePath<LocalDate> date) {
        return Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", date);
    }

    public static StringExpression getDateFromQueryLocalDateTime(DateTimeExpression<LocalDateTime> dateTime) {
        return Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", dateTime);
    }

    public static Expression<String> getDateFromDatePath(DatePath<Date> datePath) {
        String formatPattern = "yyyy-MM-dd";

        return datePath.stringValue().concat(new SimpleDateFormat(formatPattern).format(datePath));
    }
}
