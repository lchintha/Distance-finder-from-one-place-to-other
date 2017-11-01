package com.example.chint.distancefinder.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chint on 10/11/2017.
 */

public class Value {
    @SerializedName("text")
    private String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
