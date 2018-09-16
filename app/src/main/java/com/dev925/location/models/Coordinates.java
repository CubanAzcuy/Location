package com.dev925.location.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Coordinates implements Parcelable {
    public double lon;
    public double lat;


    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lon);
        dest.writeDouble(this.lat);
    }

    protected Coordinates(Parcel in) {
        this.lon = in.readDouble();
        this.lat = in.readDouble();
    }

    public static final Parcelable.Creator<Coordinates> CREATOR = new Parcelable.Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel source) {
            return new Coordinates(source);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };
}
