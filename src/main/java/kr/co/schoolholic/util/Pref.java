package kr.co.schoolholic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import kr.co.schoolholic.core.Global;

public class Pref {
	private static final String PREFS = "Preferences";
	private static SharedPreferences getPreferences() {
		Context context = Global.instance.getApplicationContext();
		return context.getSharedPreferences( PREFS, Context.MODE_PRIVATE);
	}

	private static Editor getEditor() {
		Context context = Global.instance.getApplicationContext();
		return context.getSharedPreferences( PREFS, Context.MODE_PRIVATE ).edit();
	}

	public static boolean setStringPref(String key, String value) {
		Editor editor = getEditor();
		if(editor == null)
			return false;

		editor.putString(key, value);
		editor.commit();

		return true;
	}

	public static String getStringPref(String key) {
		SharedPreferences preferences = getPreferences();
		if(preferences == null)
			return null;

		return preferences.getString(key, null);
	}
	
	public static boolean setBooleanPref(String key, boolean value) {
		Editor editor = getEditor();
		if(editor == null || key == null)
			return false;

		editor.putBoolean(key, value);
		editor.commit();

		return true;
	}
	
	public static boolean getBooleanPref(String key) {
		SharedPreferences preferences = getPreferences();
		if(preferences == null)
			return false;

		return preferences.getBoolean(key, false);
	}
	
	public static boolean getPushServicePref(String key) {
		SharedPreferences preferences = getPreferences();
		if(preferences == null)
			return false;

		return preferences.getBoolean(key, true);
	}
	
	public static boolean setIntegerPref(String key, int value) {
		Editor editor = getEditor();
		if(editor == null || key == null)
			return false;

		editor.putInt(key, value);
		editor.commit();

		return true;
	}
	
	public static int getIntegerPref(String key) {
		SharedPreferences preferences = getPreferences();
		if(preferences == null)
			return -1;

		return preferences.getInt(key, -1);
	}

	public static boolean setLongPref(String key, long value) {
		Editor editor = getEditor();
		if(editor == null || key == null)
			return false;

		editor.putLong(key, value);
		editor.commit();

		return true;
	}

	public static long getLongPref(String key) {
		SharedPreferences preferences = getPreferences();
		if(preferences == null)
			return -1;

		return preferences.getLong(key, -1);
	}
}
