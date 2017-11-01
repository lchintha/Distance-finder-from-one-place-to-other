package com.example.chint.distancefinder.Model;

import com.example.chint.distancefinder.Model.Distance;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chint on 10/11/2017.
 */

public class Details {
    @SerializedName("routes")
    private List<Distance> routes = null;

    public List<Distance> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Distance> routes) {
        this.routes = routes;
    }
}
