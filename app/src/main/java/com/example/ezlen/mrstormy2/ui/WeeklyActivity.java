package com.example.ezlen.mrstormy2.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ezlen.mrstormy2.R;
import com.example.ezlen.mrstormy2.Adapters.WeeklyAdapter;
import com.example.ezlen.mrstormy2.model.Weather.WeeklyWeather;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WeeklyActivity extends AppCompatActivity {

    private WeeklyWeather[] mDays;


    @Bind(android.R.id.list) ListView mListView;
    @Bind(android.R.id.empty) TextView mEmptyTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        ButterKnife.bind(this);

        Intent myintent = getIntent();
        Parcelable[] parcelables = myintent.getParcelableArrayExtra(WeatherActivity.WEEKLY_FORECAST);

        mDays = Arrays.copyOf(parcelables, parcelables.length, WeeklyWeather[].class);

        WeeklyAdapter myadapter = new WeeklyAdapter(this, mDays);
        mListView.setAdapter(myadapter);

        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }


//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//    }
}
