package com.openweathermap.core.weather;

import android.support.annotation.NonNull;

import com.openweathermap.core.interfaces.CurrentWeatherListener;

import okhttp3.Response;

/**
 * Created by Rohit on 25-02-2017.
 */

public interface IWeatherNetworkManager {
    public void getCurrentWeather(@NonNull String cityAndCountry, @NonNull String unit, @NonNull CurrentWeatherListener listener);
}
