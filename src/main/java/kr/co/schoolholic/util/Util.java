package kr.co.schoolholic.util;


import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;

import kr.co.schoolholic.core.Global;

public class Util {
    public static int dp2px(float dp) {
        int px = 0;
        DisplayMetrics m = Global.instance.getDisplayMetrics();

        assert m != null;

        px = (int)(dp * m.density);

        return px;
    }

    public static float px2dp(int px) {
        float dp = 0.f;
        DisplayMetrics m = Global.instance.getDisplayMetrics();

        assert m != null;

        dp = px / m.density;

        return dp;
    }

    public static int color(int resId) {
        Context context = Global.instance.getApplicationContext();
        return context.getResources().getColor(resId);
    }
}
