package com.openweathermap.core.models.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Latitude and longitude coordinates for the geo point.
 */
public class Coord {

    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;

    /**
     * @return City geo location, longitude
     */
    public Double getLon() {
        return lon;
    }

    /**
     * @return City geo location, latitude
     */
    public Double getLat() {
        return lat;
    }
}
