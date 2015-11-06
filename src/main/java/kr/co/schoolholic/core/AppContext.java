package kr.co.schoolholic.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

import kr.co.schoolholic.util.Trace;

/**
 * Created by Kevin on 2015. 2. 12..
 * AppContext instance offer the constant variables while application cycle.
 */
public enum AppContext {
    instance;

    private Context mApplicationContext;
    private Context mTopActivityContext;
    private DisplayMetrics mDisplayMetrics;
    private SQLiteDatabase mDB;

    private HashMap<String, Object> mCache = new LinkedHashMap<>();

    public void setApplicationContext(Context context) {
        mApplicationContext = context;
    }

    public Context getApplicationContext() {
        return mApplicationContext;
    }

    public void setTopActivityContext(Context context) {
        mTopActivityContext = context;
    }

    public Context getTopActivityContext() {
        return mTopActivityContext;
    }

    public DisplayMetrics getDisplayMetrics() {
        if(mDisplayMetrics == null) {
            mDisplayMetrics = new DisplayMetrics();
            WindowManager wm = (WindowManager)mApplicationContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        }

        return mDisplayMetrics;
    }

    public String getApplicationName() {
        int stringId = mApplicationContext.getApplicationInfo().labelRes;
        return mApplicationContext.getString(stringId);
    }

    public void setDB(SQLiteDatabase db) {
        mDB = db;
    }

    public SQLiteDatabase getDB() {
        return mDB;
    }

    public Object addCacheObject(String key, Object object) {
        if(mCache.containsKey(key)) {
            Trace.w("The key(" + key + ") already used.");
            return null;
        }

        return mCache.put(key, object);
    }

    public Object removeCacheObject(String key) {
        if(mCache.containsKey(key) == false) {
            Trace.w("The key isn't in cache.");
            return null;
        }

        return mCache.remove(key);
    }

    public Object getCacheObject(String key) {
        if(mCache.containsKey(key) == false) {
            Trace.w("The key isn't in cache.");
            return null;
        }

        return mCache.get(key);
    }
}
