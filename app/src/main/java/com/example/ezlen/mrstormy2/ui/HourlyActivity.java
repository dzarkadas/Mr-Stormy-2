package com.example.ezlen.mrstormy2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.ezlen.mrstormy2.R;
import com.example.ezlen.mrstormy2.Adapters.HourlyAdapter;
import com.example.ezlen.mrstormy2.model.Weather.HourlyWeather;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HourlyActivity extends AppCompatActivity {

    private HourlyWeather[] mHours;


    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly);
        ButterKnife.bind(this);

        Intent myintent = getIntent();
        Parcelable parcelables[] = myintent.getParcelableArrayExtra(WeatherActivity.HOURLY_FORECAST);

        mHours = Arrays.copyOf(parcelables, parcelables.length, HourlyWeather[].class);

        HourlyAdapter myadapter = new HourlyAdapter(this, mHours);
        mRecyclerView.setAdapter(myadapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
    }
}
