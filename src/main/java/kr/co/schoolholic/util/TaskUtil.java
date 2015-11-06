package kr.co.schoolholic.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import kr.co.schoolholic.core.AppContext;

/**
 * Created by kevin on 15. 2. 11..
 */
public enum TaskUtil implements Application.ActivityLifecycleCallbacks {
    instance;

    private boolean isApplicationInForeground = false;

    /**
     * Note that if you use this method, you shouldn't check in your Activity's onPause().
     * This is wrong place for checking Application is in the background.
     * You should check in your Activity's onStop method, after you call super.onStop().
     * @return true if application is running in foreground, false otherwise.
     */
    public boolean isApplicationInForeground() {
        return isApplicationInForeground;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Trace.d("onActivityCreated. activity name = " + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Trace.d("onActivityStarted. activity name = " + activity.getLocalClassName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Trace.d("onActivityResumed. activity name = " + activity.getLocalClassName());
        isApplicationInForeground = true;
        AppContext.instance.setTopActivityContext(activity);
    }

    @Override
    /**
     * This method will be called after super.onPause();
     */
    public void onActivityPaused(Activity activity) {
        Trace.d("onActivityPaused. activity name = " + activity.getLocalClassName());
        isApplicationInForeground = false;
    }

    @Override
    /**
     * This method will be called after super.onStop();
     */
    public void onActivityStopped(Activity activity) {
        Trace.d("onActivityStopped. activity name = " + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Trace.d("onActivitySaveInstanceState. activity name = " + activity.getLocalClassName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Trace.d("onActivityDestroyed. activity name = " + activity.getLocalClassName());
    }

}
