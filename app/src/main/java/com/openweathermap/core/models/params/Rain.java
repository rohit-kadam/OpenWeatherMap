package com.openweathermap.core.models.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h")
    @Expose
    private Double last3hVolume;

    /**
     * @return Rain volume for the last 3 hours
     */
    public Double getLast3hVolume() {
        return last3hVolume;
    }
}
