package com.openweathermap.android.weather.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lapism.searchview.SearchView;
import com.openweathermap.R;
import com.openweathermap.android.application.ObjectContainer;
import com.openweathermap.android.externalClasses.DividerItemDecoration;
import com.openweathermap.android.recyclerViewTouchListener.ClickListener;
import com.openweathermap.android.recyclerViewTouchListener.RecyclerTouchListener;
import com.openweathermap.android.utils.Constants;
import com.openweathermap.android.utils.PreferenceHelper;
import com.openweathermap.android.weather.adapters.CitiesAdapter;
import com.openweathermap.core.interfaces.IClientContainer;
import com.openweathermap.core.models.City;
import com.openweathermap.core.weather.IWeatherFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class WeatherHomeActivity extends AppCompatActivity implements SearchView.OnOpenCloseListener, SearchView.OnQueryTextListener {
    //views
    private RecyclerView recyclerViewCity;
    Toolbar toolbar;
    private SearchView searchView = null;

    //variables
    private static final String TAG = "WeatherHomeActivity";
    private List<City> cities = new ArrayList<>();
    private CitiesAdapter citiesAdapter;
    private String enterCityName = "";
    private PreferenceHelper preferenceHelper;
    private IWeatherFacade weatherFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.city_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new_city) {
            addNewCity();
            return true;
        } else if (id == R.id.action_search) {
            searchView.open(true, item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        //views
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerViewCity = (RecyclerView) findViewById(R.id.recycler_view_city);

        //variables
        preferenceHelper = new PreferenceHelper(getApplicationContext());
        weatherFacade = (IWeatherFacade) ObjectContainer.getInstance().getBean(IClientContainer.WEATHER_FACADE);

        toolbar.setTitle("Cities");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_white));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCity.setLayoutManager(mLayoutManager);
        recyclerViewCity.setItemAnimator(new DefaultItemAnimator());

        citiesAdapter = new CitiesAdapter(cities);
        recyclerViewCity.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewCity.setAdapter(citiesAdapter);
        recyclerViewCity.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewCity, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                City city = citiesAdapter.getClickedItem(position); //cities.get(position);

                Intent intent = new Intent(getApplicationContext(), CityCurrentWeatherActivity.class);
                intent.putExtra("cityName", city.getName());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, final int position) {
                City city = cities.get(position);
                final String cityName = city.getName().toLowerCase();
                if (!cityName.equalsIgnoreCase("mumbai")) {
                    Observable.just(weatherFacade.deleteCity(cityName))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<City>() {
                                @Override
                                public void call(City city) {
                                    cities.remove(position);
                                    citiesAdapter.notifyDataSetChanged();

                                    Toast.makeText(getApplicationContext(), cityName+" deleted", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "You can not delete Mumbai city", Toast.LENGTH_SHORT).show();
                }

            }
        }));

        prepareCityData();

        loadCities();

        setupSearchView();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.close(true);
        } else {
            moveTaskToBack(true);
        }
    }

    private void loadCities() {
        Observable.just(weatherFacade.getAllCities())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<City>>() {
                    @Override
                    public void call(List<City> dbCities) {
                        cities.addAll(dbCities);
                        citiesAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void prepareCityData() {
        if (!preferenceHelper.getBoolean(Constants.APP_INSTALLED_SET)) {
            weatherFacade.insertCity("mumbai");
            weatherFacade.insertCity("pune");
            weatherFacade.insertCity("delhi");
            weatherFacade.insertCity("bengaluru");
            weatherFacade.insertCity("kolkata");
            weatherFacade.insertCity("chennai");

            preferenceHelper.addBoolean(Constants.APP_INSTALLED_SET, true);
        }
    }

    private void addNewCity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add city");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterCityName = input.getText().toString();
                if (enterCityName.trim().length() > 0) {
                    int saved = weatherFacade.insertCity(enterCityName.toLowerCase());
                    if (saved == 1) {
                        Toast.makeText(getApplicationContext(), "City added successfully", Toast.LENGTH_SHORT).show();
                        cities.add(new City(enterCityName));
                        citiesAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Duplicate city name", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "City name is required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * initial of toolbar search view
     */
    private void setupSearchView() {
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setVersion(SearchView.VERSION_MENU_ITEM);
        searchView.setTheme(SearchView.THEME_LIGHT);
        searchView.setHint("Search...");
        searchView.setVoiceText("Set permission on Android 6+ !");
        searchView.setVoice(false);
        searchView.setAnimationDuration(300);
        searchView.setShouldHideOnKeyboardClose(false);
        searchView.setShadow(false);
        searchView.setVersionMargins(0);
        searchView.setHorizontalFadingEdgeEnabled(true);
        searchView.setSaveEnabled(false);

        searchView.setOnOpenCloseListener(this);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onClose() {
        citiesAdapter.getFilter().filter("");
        searchView.setQuery("", false);
        return false;
    }

    @Override
    public boolean onOpen() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        citiesAdapter.getFilter().filter(query.toLowerCase(Locale.getDefault()));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        citiesAdapter.getFilter().filter(newText.toLowerCase(Locale.getDefault()));
        return false;
    }
}
