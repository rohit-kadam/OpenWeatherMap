package com.openweathermap.core.weather;

import com.openweathermap.core.models.City;

import java.util.List;

/**
 * Created by Rohit on 25-02-2017.
 */

public interface IWeatherDBManager {
    public int insertCity(String cityName);

    public List<City> getAllCities();

    public City deleteCity(String cityName);
}
