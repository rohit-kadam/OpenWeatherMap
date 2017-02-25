package com.openweathermap.android.weather.layers;

import android.content.Context;

import com.openweathermap.android.utils.PreferenceHelper;
import com.openweathermap.core.models.City;
import com.openweathermap.core.weather.IWeatherDBManager;

import java.util.List;

/**
 * Created by Rohit on 25-02-2017.
 */

public class DefaultWeatherDBLayer implements IWeatherDBManager {
    private Context context;
    PreferenceHelper preferenceHelper;
    private static final String TAG = "DefaultWeatherDBLayer";

    public DefaultWeatherDBLayer(Context context) {
        this.context = context;
        preferenceHelper= new PreferenceHelper(context);
    }

    @Override
    public int insertCity(String cityName) {
        return City.insertCity(cityName);
    }

    @Override
    public List<City> getAllCities() {
        return City.getAll();
    }

    @Override
    public City deleteCity(String cityName) {
        return City.deleteCityByName(cityName);
    }
}
