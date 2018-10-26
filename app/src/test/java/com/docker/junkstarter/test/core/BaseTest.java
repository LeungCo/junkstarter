package com.docker.junkstarter.test.core;

import static com.docker.junkstarter.util.DateUtility.getDateMillis;

import org.joda.time.DateTimeUtils;
import org.junit.Before;

public class BaseTest {
	protected static long TODAY_MILLIS = getDateMillis("2018-05-20");

	@Before
	public void setup() throws Exception {
		DateTimeUtils.setCurrentMillisFixed(TODAY_MILLIS);
	}
}
