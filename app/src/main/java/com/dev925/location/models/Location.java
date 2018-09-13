package com.dev925.location.models;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("_id") long id;
    String country;
    String name;
    Coordinates coord;

    Location(){}
}
