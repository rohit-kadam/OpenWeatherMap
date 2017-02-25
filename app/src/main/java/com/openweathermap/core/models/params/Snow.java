package com.openweathermap.core.models.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow {

    @SerializedName("3h")
    @Expose
    private Double last3hVolume;

    /**
     * @return Snow volume for the last 3 hours
     */
    public Double getLast3hVolume() {
        return last3hVolume;
    }
}
