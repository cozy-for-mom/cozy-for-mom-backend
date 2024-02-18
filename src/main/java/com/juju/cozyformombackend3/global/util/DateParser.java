package com.juju.cozyformombackend3.global.util;

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

	public static String dateTimeToStringFormatDate(LocalDateTime localDateTime) {
		if (null == localDateTime) {
			return null;
		}
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
