package com.openweathermap.android.application;

import android.content.Context;

import com.openweathermap.android.utils.App;
import com.openweathermap.android.utils.PreferenceHelper;
import com.openweathermap.android.weather.layers.DefaultWeatherDBLayer;
import com.openweathermap.android.weather.layers.DefaultWeatherNetworkLayer;
import com.openweathermap.core.application.DefaultApplicationFacade;
import com.openweathermap.core.interfaces.IClientContainer;
import com.openweathermap.core.weather.DefaultWeatherFacade;
import com.openweathermap.core.weather.IWeatherDBManager;
import com.openweathermap.core.weather.IWeatherNetworkManager;

import java.util.HashMap;

/**
 * Created by Rohit on 25-02-2017.
 */

public class ObjectContainer implements IClientContainer {

    HashMap<Integer,Object> beanPool;
    private static ObjectContainer mInstance;

    private ObjectContainer() {
        beanPool = new HashMap<>();
    }

    /**
     * get new instance of ObjectContainer if not set otherwise it will return old instance of ObjectContainer
     * @return
     */
    public static IClientContainer getInstance() {
        if(mInstance == null) {
            mInstance = new ObjectContainer();
        }

        return mInstance;
    }

    /**
     *
     * @param beanIndentifier
     * @return object from objectPool
     */
    @Override
    public Object getBean(int beanIndentifier) {

        /**
         * check if object is present in pool
         */
        if(beanPool!=null && beanPool.containsKey(beanIndentifier)) {
            return beanPool.get(beanIndentifier);
        }

        Object bean = createOrPutItInPool(beanIndentifier);
        beanPool.put(beanIndentifier, bean);
        return bean;
    }

    @Override
    public PreferenceHelper getPreferenceValues() {
        PreferenceHelper preferenceHelper = new PreferenceHelper(App.getApp());
        return preferenceHelper;
    }

    @Override
    public Context getApplicationContext() {
        return App.getApp();
    }

    /**
     * Create new Object by creating new objects or reusing existing objects
     * @param beanIndentifier
     * @return Layer/Module Level Object
     */
    private Object createOrPutItInPool(int beanIndentifier)
    {


        switch (beanIndentifier)
        {
            case APPLICATION_FACADE:
                return new DefaultApplicationFacade(mInstance);

            case WEATHER_FACADE:
                return new DefaultWeatherFacade((IWeatherDBManager) getBean(WEATHER_DB_LAYER),(IWeatherNetworkManager) getBean(WEATHER_NETWORK_LAYER));
            case WEATHER_DB_LAYER:
                return new DefaultWeatherDBLayer(App.getApp());
            case WEATHER_NETWORK_LAYER:
                return new DefaultWeatherNetworkLayer(App.getApp());

        }

        return null;
    }
}
