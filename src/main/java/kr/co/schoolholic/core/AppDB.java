package kr.co.schoolholic.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kevin on 2015. 11. 6..
 */
public class AppDB extends SQLiteOpenHelper {

    private SQLiteDatabase mDB;

    public AppDB(Context context, String name, int ver) {
        super(context, name, null, ver);

        mDB = getWritableDatabase();

        AppContext.instance.setDB(mDB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
