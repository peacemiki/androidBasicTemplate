package kr.co.schoolholic.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Date;

import kr.co.schoolholic.BuildConfig;


public class AlarmUtil {
	public static final String EXTRA_URI = "uri";
	
	public static boolean setAlarm( Context context, String action, long uri, long time, boolean isSet ) {
		Intent intent = new Intent();
		intent.setAction("kr.co.schoolholic.alarmreceiver");
		intent.putExtra("action", action);
		intent.putExtra(EXTRA_URI, uri);
		
		int requestId = makeUniqueIdUsingTime(time, uri) + 10000;
		
		PendingIntent sender = PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
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
        
		return true;
    }
	
	public static boolean setRepeatAlarm( Context context, String action, long uri, long id, long alarmTime, long repeatInterval, boolean isSet ) {
		Intent intent = new Intent();
		intent.setAction("kr.co.schoolholic.alarmreceiver");
		intent.putExtra("action", action);
		intent.putExtra(EXTRA_URI, uri);
		
		int requestId = makeUniqueIdUsingTime(id, uri);
		
		PendingIntent sender = PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager am = null;

		if( isSet == true) {
			Trace.d("setRepeatAlarm = " + new Date(alarmTime).toString());
			am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			am.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, repeatInterval, sender);
		} else {
			am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			am.cancel(sender);
		}
        
		return true;
    }

	
	public static int makeUniqueIdUsingTime(long time, long uri) {
		int requestId = (int) (time/ Integer.MAX_VALUE + time%(1000*100*100*100));
		return requestId + (int)uri;
	}
}
