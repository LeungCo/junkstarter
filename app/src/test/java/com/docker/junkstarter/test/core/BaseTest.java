package com.docker.junkstarter.test.core;

import static com.docker.junkstarter.util.DateUtility.getDateMillis;

import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseTest {
	protected static String TODAY_STRING = "2018-05-20";
	protected static long TODAY_MILLIS = getDateMillis(TODAY_STRING);

	@Before
	public void setup() throws Exception {
		DateTimeUtils.setCurrentMillisFixed(TODAY_MILLIS);
	}
}
