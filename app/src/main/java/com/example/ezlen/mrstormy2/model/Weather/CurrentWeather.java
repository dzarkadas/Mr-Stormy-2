package com.example.ezlen.mrstormy2.model.Weather;

import com.example.ezlen.mrstormy2.R;
import com.example.ezlen.mrstormy2.model.Forecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentWeather {
    private long mTime;
    private double mTemp;
    private double mHumid;
    private double mPrecipChance;
    private String mIcon;
    private String mSummary;
    private String mTimezone;


    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public void setTemp(double temp) {
        mTemp = temp;
    }

    public void setHumid(double humid) {
        mHumid = humid;
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public String getIcon() {
        return mIcon;
    }

    public long getTime() {
        return mTime;
    }

    public double getTemp() {
        return ((mTemp - 32) * 5 / 9);
    }

    public double getHumid() {
        return (mHumid * 100);
    }

    public double getPrecipChance() {
        return (mPrecipChance * 100);
    }

    public String getSummary() {
        return mSummary;
    }


    public String getFormattedTime() {
        Date dateTime = new Date(getTime() * 1000);

        SimpleDateFormat ftime = new SimpleDateFormat("H:mm");
        ftime.setTimeZone(TimeZone.getTimeZone(getTimezone()));

        return ftime.format(dateTime);
    }

    public int getIconId() {
        return Forecast.getIconId(mIcon);
    }
}
