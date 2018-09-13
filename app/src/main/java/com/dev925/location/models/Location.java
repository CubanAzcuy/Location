package com.dev925.location.models;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("_id") long id;
    public String country;
    public String name;
    public Coordinates coord;

    Location(){}

    @Override
    public String toString() {
        return name + ", " + country;
    }
}
