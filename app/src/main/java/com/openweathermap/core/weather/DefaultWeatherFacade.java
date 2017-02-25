package com.openweathermap.core.weather;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.openweathermap.android.utils.Constants;
import com.openweathermap.core.interfaces.CurrentWeatherListener;
import com.openweathermap.core.models.City;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Rohit on 25-02-2017.
 */
public class DefaultWeatherFacade implements IWeatherFacade
{
    IWeatherDBManager dBManager;
    IWeatherNetworkManager networkManager;
    private static final String TAG = "DefaultWeatherFacade";

    public DefaultWeatherFacade(IWeatherDBManager dBManager, IWeatherNetworkManager networkManager) {
        this.dBManager = dBManager;
        this.networkManager = networkManager;
    }

    @Override
    public void getCurrentWeather(String cityName, CurrentWeatherListener listener) {
        networkManager.getCurrentWeather(cityName, "metric", listener);
    }

    @Override
    public void getCurrentWeather(String cityName, String countryCode, CurrentWeatherListener listener) {
        networkManager.getCurrentWeather((cityName + countryCode), "metric", listener);
    }

    @Override
    public int insertCity(String cityName) {
        return dBManager.insertCity(cityName);
    }

    @Override
    public City deleteCity(String cityName) {
        return dBManager.deleteCity(cityName);
    }

    @Override
    public List<City> getAllCities() {
        return dBManager.getAllCities();
    }
}
