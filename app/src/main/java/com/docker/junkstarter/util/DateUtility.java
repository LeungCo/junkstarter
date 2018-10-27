package com.docker.junkstarter.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtility {
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static final DateTimeFormatter DATE_FORMAT_DB_STRING = DateTimeFormat.forPattern("yyyymmdd");

	public static String getDatabaseString() {
		DateTime dt = new DateTime();
		return DATE_FORMAT_DB_STRING.print(dt);
	}

	public static String getDatabaseString(DateTime date) {
		return DATE_FORMAT_DB_STRING.print(date);
	}

	public static String getDateString() {
		return DATE_FORMAT.print(new DateTime());
	}

	public static String getDateString(Date date) {
		return DATE_FORMAT.print(new DateTime(date));
	}

	public static String getDateString(DateTime date) {
		return DATE_FORMAT.print(date);
	}

	public static long getDateMillis() {
		return new Date().getTime();
	}

	public static long getDateMillis(String date) {
		return DATE_FORMAT.parseDateTime(date).getMillis();
	}
}
