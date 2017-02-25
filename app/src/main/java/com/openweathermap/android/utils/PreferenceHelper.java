package com.openweathermap.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Rohit on 25-02-2017.
 * class use to add values in preference helper
 */

public class PreferenceHelper {

    private SharedPreferences preferences;

    public PreferenceHelper(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * add string in SharedPreferences
     * @param key
     * @param value
     */
    public void addString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * add int in SharedPreferences
     * @param key
     * @param value
     */
    public void addInteger(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * add long in SharedPreferences
     * @param key
     * @param value
     */
    public void addLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * add boolean in SharedPreferences
     * @param key
     * @param value
     */
    public void addBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * clear SharedPreferences values
     */
    public void clear() {
        // here you get your prefrences by either of two methods
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public int getInteger(String key) {
        return preferences.getInt(key, 0);
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public Boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }
}
