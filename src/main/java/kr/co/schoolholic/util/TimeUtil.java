package kr.co.schoolholic.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static final long OneDay = 60 * 60 * 24 * 1000;

	public static long getUniqueMillsOfToday() {
		Calendar today = Calendar.getInstance();
        return getUniqueMillsOfToday(today);
	}
	
	public static long getUniqueMillsOfToday(Calendar c) {
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

    public static int daysBetween(Calendar to, Calendar from) {
        if(from == null || to == null) {
            return 0;
        }

        if(from.getTimeInMillis() > to.getTimeInMillis()) {
            Calendar tmp = from;
            from = to;
            to = tmp;
        }

        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        from.set(Calendar.MILLISECOND, 0);

        long gap = to.getTimeInMillis() - from.getTimeInMillis();

        if(gap < 0)
            return (int)(Math.abs(gap) / OneDay) * -1;

        return (int)(gap / OneDay);
    }

    public static int daysBetween(Date to, Date from) {
        if(from == null || to == null) {
            return 0;
        }

        Calendar toCal = Calendar.getInstance();
        Calendar fromCal = Calendar.getInstance();

        toCal.setTime(to);
        fromCal.setTime(from);

        return daysBetween(toCal, fromCal);
    }
}
