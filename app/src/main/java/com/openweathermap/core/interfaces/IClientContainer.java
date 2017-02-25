package com.openweathermap.core.interfaces;

import android.content.Context;

import com.openweathermap.android.utils.PreferenceHelper;

/**
 * Created by Rohit on 25-02-2017.
 */

public interface IClientContainer {
    public static final int APPLICATION_FACADE = 1;
    public static final int WEATHER_FACADE = 2;
    public static final int WEATHER_DB_LAYER = 3;
    public static final int WEATHER_NETWORK_LAYER = 4;

    public Object getBean(int beanIndentifier);
    public PreferenceHelper getPreferenceValues();
    public Context getApplicationContext();
}
