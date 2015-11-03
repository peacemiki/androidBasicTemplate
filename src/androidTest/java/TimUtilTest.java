import junit.framework.TestCase;

import java.util.Calendar;

import kr.co.schoolholic.util.TimeUtil;

/**
 * Created by kevin on 2015. 11. 3..
 */
public class TimUtilTest extends TestCase {
    public void testDaysBetween() {
        Calendar to = Calendar.getInstance();
        Calendar from = Calendar.getInstance();

        to.set(Calendar.DAY_OF_MONTH, 12);
        to.set(Calendar.HOUR_OF_DAY, 12);

        from.set(Calendar.DAY_OF_MONTH, 11);
        from.set(Calendar.HOUR_OF_DAY, 18);

        assertEquals(1, TimeUtil.daysBetween(to, from));
        from.setTimeInMillis(from.getTimeInMillis() - TimeUtil.OneDay);

        assertEquals(2, TimeUtil.daysBetween(to, from));
        from.setTimeInMillis(from.getTimeInMillis() - TimeUtil.OneDay);

        assertEquals(3, TimeUtil.daysBetween(to, from));
        from.setTimeInMillis(from.getTimeInMillis() - TimeUtil.OneDay);

        assertEquals(4, TimeUtil.daysBetween(to, from));
        from.setTimeInMillis(from.getTimeInMillis() - TimeUtil.OneDay);
    }
}
