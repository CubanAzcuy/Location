package com.dev925.location.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Location implements Parcelable {

    @SerializedName("_id") long id;
    public String country;
    public String name;
    public Coordinates coord;

    Location(){}

    public Location(String name, String country, Coordinates coordinates) {
        this.name = name;
        this.country = country;
        this.coord = coordinates;
    }

    @Override
    public String toString() {
        return name + ", " + country;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.country);
        dest.writeString(this.name);
        dest.writeParcelable(this.coord, flags);
    }

    protected Location(Parcel in) {
        this.id = in.readLong();
        this.country = in.readString();
        this.name = in.readString();
        this.coord = in.readParcelable(Coordinates.class.getClassLoader());
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
