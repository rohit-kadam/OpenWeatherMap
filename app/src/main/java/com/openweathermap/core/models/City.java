package com.openweathermap.core.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Rohit on 25-02-2017.
 */

@Table(name = "City")
public class City extends Model {
    @Column(name = "Name")
    private String name;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int insertCity(String cityName) {
        if (getCityByName(cityName) == null) {
            City city = new City();
            city.setName(cityName);
            city.save();

            return 1;
        } else {
            return 0;
        }
    }

    public static City getCityByName(String cityName) {
        return new Select()
                .from(City.class)
                .where("name LIKE ?", new String[]{'%' + cityName + '%'})
                .orderBy("RANDOM()")
                .executeSingle();
    }

    public static List<City> getAll() {
        return new Select()
                .from(City.class)
                .orderBy("Name ASC")
                .execute();
    }

    public static City deleteCityByName(String cityName) {
        City city = getCityByName(cityName);
        city.delete();
        return null;
    }
}
