package com.juju.cozyformombackend3.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateParser {

	public static String dateTimeToStringFormatDateTime(LocalDateTime localDateTime) {
		if (null == localDateTime) {
			return null;
		}
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public static String dateTimeToStringFormatDate(LocalDate localDate) {
		if (null == localDate) {
			return null;
		}
		return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public static LocalDateTime stringToLocalDateTime(String datetime) {
		String formatPattern = "yyyy-MM-dd HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

		return LocalDateTime.parse(datetime, formatter);
	}

	public static LocalDate stringToLocalDate(String date) {
		String formatPattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

		return LocalDate.parse(date, formatter);
	}
}
