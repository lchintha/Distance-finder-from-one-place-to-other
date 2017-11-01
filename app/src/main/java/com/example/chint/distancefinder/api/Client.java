package com.example.chint.distancefinder.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chint on 10/11/2017.
 */

public class Client {
    public static final String BASE_URL = "http://maps.googleapis.com/";

    //http://maps.googleapis.com/maps/api/directions/json?origin=39.004554,-76.875528&destination=39.290385,-76.612189

    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
