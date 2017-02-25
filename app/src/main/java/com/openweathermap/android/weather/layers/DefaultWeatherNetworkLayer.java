package com.openweathermap.android.weather.layers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.openweathermap.android.utils.Constants;
import com.openweathermap.android.utils.PreferenceHelper;
import com.openweathermap.core.interfaces.CurrentWeatherListener;
import com.openweathermap.core.models.CurrentWeather;
import com.openweathermap.core.weather.IWeatherNetworkManager;

import java.io.IOException;

import okhttp3.HttpUrl;
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

public class DefaultWeatherNetworkLayer implements IWeatherNetworkManager {
    private Context context;
    private PreferenceHelper preferenceHelper;
    private static final String TAG = "DefaultWeatherNetworkLa";

    public DefaultWeatherNetworkLayer(Context context) {
        this.context = context;
        preferenceHelper= new PreferenceHelper(context);
    }

    @Override
    public void getCurrentWeather(final @NonNull String cityAndCountry, final @NonNull String unit, final @NonNull CurrentWeatherListener listener) {
        Observable.create(new Observable.OnSubscribe<Response>() {
            OkHttpClient client = new OkHttpClient();
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    String requestUrl = Constants.BASE_URL+"/weather?q="+cityAndCountry+"&appid="+ Constants.OPEN_WEATHER_MAP_APIKEY +"&units="+unit;
                    Response response = client.newCall(new Request.Builder().url(requestUrl).build()).execute();
                    subscriber.onNext(response);
                    subscriber.onCompleted();
                    if (!response.isSuccessful()) subscriber.onError(new Exception("error"));
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Response>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.onError("error");
            }

            @Override
            public void onNext(Response response) {
                try {
                    listener.onResponse(new Gson().fromJson(response.body().string(), CurrentWeather.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                });
    }
}
