package com.example.ezlen.mrstormy2.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ezlen.mrstormy2.model.Forecast;
import com.example.ezlen.mrstormy2.model.Weather.CurrentWeather;
import com.example.ezlen.mrstormy2.ui.Dialogs.AlertDialogFragment;
import com.example.ezlen.mrstormy2.ui.Dialogs.NetDialogFragment;
import com.example.ezlen.mrstormy2.R;
import com.example.ezlen.mrstormy2.model.Weather.WeeklyWeather;
import com.example.ezlen.mrstormy2.model.Weather.HourlyWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherActivity extends AppCompatActivity {

    private Forecast mForecast;
    public static final String WEEKLY_FORECAST = "WEEKLY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";


    @Bind(R.id.timeTextView) TextView mTimeLabel;
    @Bind(R.id.tempTextView) TextView mTempLabel;
    @Bind(R.id.humidTextView) TextView mHumidValue;
    @Bind(R.id.precipTextView) TextView mPrecipValue;
    @Bind(R.id.sumTextView) TextView mSumLabel;
    @Bind(R.id.iconImageView) ImageView mIconImageView;
    @Bind(R.id.refreshImageView) ImageView mRefreshImageView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.backImageView) ImageView mBackImageView;
    @Bind(R.id.locationTextView) TextView mLocationTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        Intent myintent = getIntent();
        final String loc = myintent.getStringExtra("city");
        final double llat = myintent.getDoubleExtra("latitude", 38.246639);
        final double llong = myintent.getDoubleExtra("longitude", 21.734573);


        mProgressBar.setVisibility(View.INVISIBLE);

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(loc, llat, llong);
            }
        });


//        mBackImageView.setVisibility(View.INVISIBLE);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getForecast(loc, llat, llong);
    }


    private void getForecast(String location, double apiLAT, double apiLONG) {
        String apiKEY = "1372f5ed05bfdd7f3f52bc17dda22e44";
        String apiURL = "https://api.forecast.io/forecast/" + apiKEY + "/" + apiLAT + "," + apiLONG;

        mLocationTextView.setText(location);


        if (isNetAvailable()) {
            toggleRefresh();

            OkHttpClient myclient = new OkHttpClient();
            Request myrequest = new Request.Builder()
                    .url(apiURL)
                    .build();

            Call mycall = myclient.newCall(myrequest);
            mycall.enqueue(new Callback() {
                @Override
                public void onFailure(Call mycall, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    alertError();
                }

                @Override
                public void onResponse(Call mycall, Response myresponse) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    String jSonData = myresponse.body().string();

                    if (myresponse.isSuccessful()) {
                        try {
                            mForecast = parseForecastDetails(jSonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } catch (JSONException e) {
                            alertError();
                        }
                    } else {
                        alertError();
                    }
                }
            });
        }
        else {
            alertNetError();
        }
    }


    private Forecast parseForecastDetails(String jSonData) throws JSONException {
        Forecast totalForecast = new Forecast();

        totalForecast.setCurrentWeather(getCurrentDetails(jSonData));
        totalForecast.setHourlyWeathers(getHourlyDetails(jSonData));
        totalForecast.setWeeklyWeathers(getWeeklyDetails(jSonData));

        return totalForecast;
    }

    private CurrentWeather getCurrentDetails(String jSonData) throws JSONException {
        JSONObject forecast = new JSONObject(jSonData);
        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather curWeather = new CurrentWeather();
        curWeather.setTime(currently.getLong("time"));
        curWeather.setHumid(currently.getDouble("humidity"));
        curWeather.setTemp(currently.getDouble("temperature"));
        curWeather.setPrecipChance(currently.getDouble("precipProbability"));
        curWeather.setIcon(currently.getString("icon"));
        curWeather.setSummary(currently.getString("summary"));
        curWeather.setTimezone(forecast.getString("timezone"));

        return curWeather;
    }

    private HourlyWeather[] getHourlyDetails(String jSonData) throws JSONException {
        JSONObject forecast = new JSONObject(jSonData);
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        HourlyWeather[] hours = new HourlyWeather[data.length()];

        for (int i = 0; i < data.length(); ++i) {
            JSONObject hourly_data_hour = data.getJSONObject(i);

            HourlyWeather hourWeather = new HourlyWeather();
            hourWeather.setTime(hourly_data_hour.getLong("time"));
            hourWeather.setTemp(hourly_data_hour.getDouble("temperature"));
            hourWeather.setIcon(hourly_data_hour.getString("icon"));
            hourWeather.setSummary(hourly_data_hour.getString("summary"));
            hourWeather.setTimezone(forecast.getString("timezone"));

            hours[i] = hourWeather;
        }

        return hours;
    }

    private WeeklyWeather[] getWeeklyDetails(String jSonData) throws JSONException {
        JSONObject forecast = new JSONObject(jSonData);
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        WeeklyWeather[] days = new WeeklyWeather[data.length()];

        for (int i = 0; i < data.length(); ++i) {
            JSONObject daily_data_day = data.getJSONObject(i);

            WeeklyWeather dayWeather = new WeeklyWeather();
            dayWeather.setTime(daily_data_day.getLong("time"));
            dayWeather.setMaxTemp(daily_data_day.getDouble("temperatureMax"));
            dayWeather.setIcon(daily_data_day.getString("icon"));
            dayWeather.setSummary(daily_data_day.getString("summary"));
            dayWeather.setTimezone(forecast.getString("timezone"));

            days[i] = dayWeather;
        }

        return days;



    }

    private void updateDisplay() {
        CurrentWeather current = mForecast.getCurrentWeather();

        mTempLabel.setText(String.format("%.1f", current.getTemp()));
        mTimeLabel.setText("At " + current.getFormattedTime() + " it will be :");
        mHumidValue.setText(String.format("%.1f", current.getHumid()) + "%");
        mPrecipValue.setText(String.format("%.1f", current.getPrecipChance()) + "%");
        mSumLabel.setText("The weather at the moment is " + current.getSummary());

        Drawable iconDraw = getResources().getDrawable(current.getIconId());
        mIconImageView.setImageDrawable(iconDraw);
    }


    private boolean isNetAvailable() {
        ConnectivityManager mymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mymanager.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    private void alertError() {
        AlertDialogFragment mydialog = new AlertDialogFragment();
        mydialog.show(getFragmentManager(), "error_dialog");
    }

    private void alertNetError() {
        NetDialogFragment mydialog = new NetDialogFragment();
        mydialog.show(getFragmentManager(), "net_error_dialog");
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
    }


    @OnClick(R.id.weeklyButton)
    public void startWeeklyActivity(View view) {
        Intent myintent = new Intent(this, WeeklyActivity.class);
        myintent.putExtra(WEEKLY_FORECAST, mForecast.getWeeklyWeathers());

        startActivity(myintent);
    }

    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view) {
        Intent myintent = new Intent(this, HourlyActivity.class);
        myintent.putExtra(HOURLY_FORECAST, mForecast.getHourlyWeathers());

        startActivity(myintent);
    }
}
