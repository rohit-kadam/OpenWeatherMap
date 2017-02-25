package com.openweathermap.android.weather.view.activities;

import android.animation.ObjectAnimator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.openweathermap.R;
import com.openweathermap.android.application.ObjectContainer;
import com.openweathermap.android.utils.App;
import com.openweathermap.core.interfaces.CurrentWeatherListener;
import com.openweathermap.core.interfaces.IClientContainer;
import com.openweathermap.core.models.CurrentWeather;
import com.openweathermap.core.weather.IWeatherFacade;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CityCurrentWeatherActivity extends AppCompatActivity {
    //views
    ArcProgress arcProgressTemperature;
    Toolbar toolbar;
    TextView txtMinTemperature, txtMaxTemperature, txtWindSpeed;

    //variables
    private static final String TAG = "CityCurrentWeather";
    private IWeatherFacade weatherFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_current_weather);

        init();
    }

    /**
     * method use to initialize views and variables related to current class
     */
    private void init() {
        //views
        arcProgressTemperature = (ArcProgress) findViewById(R.id.arc_progress_temperature);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtMinTemperature = (TextView) findViewById(R.id.txt_min_temperature);
        txtMaxTemperature = (TextView) findViewById(R.id.txt_max_temperature);
        txtWindSpeed = (TextView) findViewById(R.id.txt_wind_speed);

        weatherFacade = (IWeatherFacade) ObjectContainer.getInstance().getBean(IClientContainer.WEATHER_FACADE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("cityName")) {
            String cityName = bundle.getString("cityName");
            toolbar.setTitle(App.firstLetterCapital(cityName));
            getCurrentWeatherByCityName(cityName);
        } else {
            toolbar.setTitle("No city");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_arrow_back_white));
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getCurrentWeatherByCityName(String cityName) {
        weatherFacade.getCurrentWeather(cityName, new CurrentWeatherListener() {
            @Override
            public void onResponse(CurrentWeather currentWeather) {
                txtMinTemperature.setText("Min temperature: "+currentWeather.getMain().getTempMin() + " " + getString(R.string.degree_celsius));
                txtMaxTemperature.setText("Max temperature: "+currentWeather.getMain().getTempMax() + " " + getString(R.string.degree_celsius));
                txtWindSpeed.setText("Wind speed: "+currentWeather.getWindInfo().getSpeed() + " m/s");

                //temperature arc animation
                ObjectAnimator anim = ObjectAnimator.ofInt(arcProgressTemperature, "progress", 0, currentWeather.getMain().getTemp().intValue());
                anim.setInterpolator(new DecelerateInterpolator());
                anim.setDuration(500);
                anim.start();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(CityCurrentWeatherActivity.this, "Oops, something went wrong please try again", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: message "+message);
            }
        });
    }
}
