package com.example.ezlen.mrstormy2.model;

import com.example.ezlen.mrstormy2.R;
import com.example.ezlen.mrstormy2.model.Weather.CurrentWeather;
import com.example.ezlen.mrstormy2.model.Weather.WeeklyWeather;
import com.example.ezlen.mrstormy2.model.Weather.HourlyWeather;

public class Forecast {
    private CurrentWeather mCurrentWeather;
    private HourlyWeather[] mHourlyWeathers;
    private WeeklyWeather[] mWeeklyWeathers;

    public CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        mCurrentWeather = currentWeather;
    }

    public HourlyWeather[] getHourlyWeathers() {
        return mHourlyWeathers;
    }

    public void setHourlyWeathers(HourlyWeather[] hourlyWeathers) {
        mHourlyWeathers = hourlyWeathers;
    }

    public WeeklyWeather[] getWeeklyWeathers() {
        return mWeeklyWeathers;
    }

    public void setWeeklyWeathers(WeeklyWeather[] weeklyWeathers) {
        mWeeklyWeathers = weeklyWeathers;
    }


    public static int getIconId(String iconString) {
        int iconId = R.drawable.clear_day;

        switch (iconString) {
            case "clear-day":
                iconId = R.drawable.clear_day;
                break;
            case "clear-night":
                iconId = R.drawable.clear_night;
                break;
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "sleet":
                iconId = R.drawable.sleet;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.fog;
                break;
            case "cloudy":
                iconId = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;
        }

        return iconId;
    }
}
