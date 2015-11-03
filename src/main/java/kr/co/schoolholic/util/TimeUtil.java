package kr.co.schoolholic.util;

import java.util.Calendar;

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

        return (int)(gap / OneDay);
    }
}
