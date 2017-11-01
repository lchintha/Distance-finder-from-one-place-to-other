package com.example.chint.distancefinder.api;

import com.example.chint.distancefinder.Model.Details;
import com.example.chint.distancefinder.Model.Value;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chint on 10/11/2017.
 */

public interface Service {
    @GET("maps/api/directions/json")
    Call<Details> getDetailsInfo(@Query("origin") String origin, @Query("destination") String destination);

    //http://maps.googleapis.com/maps/api/directions/json?origin=39.004554,-76.875528&destination=39.290385,-76.612189

    //?origin=39.004554,-76.875528&destination=39.290385,-76.612189
}
