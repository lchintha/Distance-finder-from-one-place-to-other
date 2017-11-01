package com.example.chint.distancefinder.Model;

import com.google.gson.annotations.SerializedName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chint on 10/11/2017.
 */

public class Text {

    @SerializedName("distance")
    private Value result;

    @SerializedName("duration")
    private Time time;

    public Value getResult() {
        return result;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setResult(Value distance) {
        this.result = distance;

    }
}
