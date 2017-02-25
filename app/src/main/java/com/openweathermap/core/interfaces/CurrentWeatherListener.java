package com.openweathermap.core.interfaces;

import com.openweathermap.core.models.CurrentWeather;

/**
 * Created by Rohit on 25-02-2017.
 */

public interface CurrentWeatherListener {
    void onResponse(CurrentWeather currentWeather);
    void onError(String message);
}
