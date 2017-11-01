package com.example.chint.distancefinder.Model;

import com.example.chint.distancefinder.Model.Text;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chint on 10/11/2017.
 */

public class Distance {
    @SerializedName("legs")
    private List<Text> legs = null;

    public List<Text> getLegs() {
        return legs;
    }

    public void setLegs(List<Text> legs) {
        this.legs = legs;
    }
}
