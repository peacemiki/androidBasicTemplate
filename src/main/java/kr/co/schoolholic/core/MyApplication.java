package kr.co.schoolholic.core;

import android.app.Application;

import kr.co.schoolholic.util.TaskUtil;


/**
 * Created by kevin on 15. 2. 11..
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppContext.instance.setApplicationContext(getApplicationContext());

        registerActivityLifecycleCallbacks(TaskUtil.instance);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        AppContext.instance.setApplicationContext(null);
    }

}
