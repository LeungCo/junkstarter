package com.docker.junkstarter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_DB_STRING = new SimpleDateFormat("YYYYMMDD");

	public static String getDatabaseString() {
		return DATE_FORMAT_DB_STRING.format(new Date());
	}

	public static String getDatabaseString(Date date) {
		return DATE_FORMAT_DB_STRING.format(date);
	}

	public static String getDateString() {
		return DATE_FORMAT.format(new Date());
	}

	public static String getDateString(Date date) {
		return DATE_FORMAT.format(date);
	}

	public static long getDateMillis(String date) {
		Date today;
		try {
			today = DATE_FORMAT.parse(date);
			long millis = today.getTime();
			return millis;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0L;
	}
}
