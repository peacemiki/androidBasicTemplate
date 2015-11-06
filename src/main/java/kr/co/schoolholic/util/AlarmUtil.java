package kr.co.schoolholic.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Date;

import kr.co.schoolholic.core.AppContext;


public class AlarmUtil {
	public static void setAlarm( Intent intent, int identifier, long time, boolean isSet ) {
        Context context = AppContext.instance.getApplicationContext();

		PendingIntent sender = PendingIntent.getBroadcast(context, identifier, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

		if( isSet == true ) {
			Trace.d("setAlarm = " + new Date(time).toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                am.setExact(AlarmManager.RTC_WAKEUP, time, sender);
            }
            else {
                am.set(AlarmManager.RTC_WAKEUP, time, sender);
            }
		} else {
        	am.cancel(sender);
		}
    }

//    public static void setRepeatAlarm( Intent intent, int identifier, long time, long interval, boolean isSet ) {
//
//    }
}
