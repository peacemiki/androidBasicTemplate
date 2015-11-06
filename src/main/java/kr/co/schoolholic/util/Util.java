package kr.co.schoolholic.util;


import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.UUID;

import kr.co.schoolholic.core.AppContext;

public class Util {
    public static int dp2px(float dp) {
        int px = 0;
        DisplayMetrics m = AppContext.instance.getDisplayMetrics();

        assert m != null;

        px = (int)(dp * m.density);

        return px;
    }

    public static float px2dp(int px) {
        float dp = 0.f;
        DisplayMetrics m = AppContext.instance.getDisplayMetrics();

        assert m != null;

        dp = px / m.density;

        return dp;
    }

    public static int color(int resId) {
        Context context = AppContext.instance.getApplicationContext();
        return context.getResources().getColor(resId);
    }

    public static String uniqueID() {
        Context context = AppContext.instance.getApplicationContext();
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }
}
