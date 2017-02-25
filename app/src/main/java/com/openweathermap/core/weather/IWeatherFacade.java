package com.openweathermap.core.weather;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.openweathermap.core.interfaces.CurrentWeatherListener;
import com.openweathermap.core.models.City;

import java.util.List;

/**
 * Created by Rohit on 25-02-2017.
 */

public interface IWeatherFacade {
    public void getCurrentWeather(String cityName, CurrentWeatherListener listener);

    public void getCurrentWeather(String cityName, String countryCode, CurrentWeatherListener listener);

    public int insertCity(String cityName);

    public City deleteCity(String cityName);

    public List<City> getAllCities();
}
