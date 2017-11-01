package com.example.chint.distancefinder.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chint on 10/11/2017.
 */

public class Time {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
