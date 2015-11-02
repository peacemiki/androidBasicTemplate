package kr.co.schoolholic.util;

import java.util.Calendar;

public class TimeUtil {
	private static final long MINUTE = 60 * 1000;
	public static long getUniqueMillsOfToday() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		long time = today.getTimeInMillis();
		return time - time % MINUTE;
	}
	
	public static long getUniqueMillsOfToday(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		long time = c.getTimeInMillis();
		return time - time % MINUTE;
	}
}
