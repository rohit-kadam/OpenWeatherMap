package com.openweathermap.android.utils;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import okhttp3.OkHttpClient;

/**
 * Created by Rohit on 25-02-2017.
 */

public class App extends Application {
    private static App app;
    private static OkHttpClient client = null;

    @Override
    public void onCreate() {
        super.onCreate();

        //initialize ActiveAndroid
        ActiveAndroid.initialize(this);

        app = this;
    }

    public static App getApp() {
        return app;
    }

    public static OkHttpClient getOkHttpInstance() {
        if (client == null)
            client = new OkHttpClient();

        return client;
    }

    public static String firstLetterCapital(String name) {
        StringBuilder sb = new StringBuilder(name);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }
}
