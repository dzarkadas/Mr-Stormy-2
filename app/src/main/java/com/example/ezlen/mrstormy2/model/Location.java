package com.example.ezlen.mrstormy2.model;

public class Location {
    private String mCity;
    private double mLong;
    private double mLat;

    public void setLat(double lat) {
        mLat = lat;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public void setLong(double aLong) {
        mLong = aLong;
    }

    public String getCity() {
        return mCity;
    }

    public double getLong() {
        return mLong;
    }

    public double getLat() {
        return mLat;
    }
}
