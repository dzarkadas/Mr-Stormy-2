package com.example.ezlen.mrstormy2.model.Weather;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ezlen.mrstormy2.model.Forecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WeeklyWeather implements Parcelable {
    private long mTime;
    private double mMaxTemp;
    private String mSummary;
    private String mIcon;
    private String mTimezone;


    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public double getMaxTemp() {
        return ((mMaxTemp - 32) * 5 / 9);
    }

    public void setMaxTemp(double maxTemp) {
        mMaxTemp = maxTemp;
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

    public String getDayOfTheWeek() {
        Date daytime = new Date(mTime * 1000);

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));

        return formatter.format(daytime);
    }



    @Override   //  NOT USING
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeDouble(mMaxTemp);
        dest.writeString(mSummary);
        dest.writeString(mTimezone);
        dest.writeString(mIcon);
    }

    private WeeklyWeather (Parcel in) {
        mTime = in.readLong();
        mMaxTemp = in.readDouble();
        mSummary = in.readString();
        mTimezone = in.readString();
        mIcon = in.readString();
    }

    public WeeklyWeather () {}

    public static final Creator<WeeklyWeather> CREATOR = new Creator<WeeklyWeather>() {
        @Override
        public WeeklyWeather createFromParcel(Parcel source) {
            return new WeeklyWeather(source);
        }

        @Override
        public WeeklyWeather[] newArray(int size) {
            return new WeeklyWeather[size];
        }
    };
}
