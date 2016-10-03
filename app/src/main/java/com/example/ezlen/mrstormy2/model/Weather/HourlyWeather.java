package com.example.ezlen.mrstormy2.model.Weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ezlen.mrstormy2.model.Forecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HourlyWeather implements Parcelable{
    private long mTime;
    private double mTemp;
    private String mSummary;
    private String mIcon;
    private String mTimezone;


    public HourlyWeather () {}

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public double getTemp() {
        return ((mTemp - 32) * 5 / 9);
    }

    public void setTemp(double temp) {
        mTemp = temp;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }


    public int getIconId() {
        return Forecast.getIconId(mIcon);
    }

    public String getFormattedHour() {
        Date daytime = new Date(mTime * 1000);

        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));

        return formatter.format(daytime);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeDouble(mTemp);
        dest.writeString(mSummary);
        dest.writeString(mTimezone);
        dest.writeString(mIcon);
    }

    private HourlyWeather (Parcel in) {
        mTime = in.readLong();
        mTemp = in.readDouble();
        mSummary = in.readString();
        mTimezone = in.readString();
        mIcon = in.readString();
    }

    public static final Creator<HourlyWeather> CREATOR = new Creator<HourlyWeather>() {
        @Override
        public HourlyWeather createFromParcel(Parcel source) {
            return new HourlyWeather(source);
        }

        @Override
        public HourlyWeather[] newArray(int size) {
            return new HourlyWeather[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }
}
