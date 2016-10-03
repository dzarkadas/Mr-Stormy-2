package com.example.ezlen.mrstormy2.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.ezlen.mrstormy2.ui.Dialogs.AlertDialogFragment;
import com.example.ezlen.mrstormy2.ui.Dialogs.NetDialogFragment;
import com.example.ezlen.mrstormy2.model.Location;
import com.example.ezlen.mrstormy2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private Location mLocation = new Location();


    @Bind(R.id.locationEditText) EditText mLocationText;
    @Bind(R.id.startButton) Button mStartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locIn = mLocationText.getText().toString();

                findCoordinates(locIn);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mLocationText.setText("");
    }


    private void findCoordinates(String userIn) {
        String apiURL = "http://maps.google.com/maps/api/geocode/json?address=" + userIn + "&sensor=false";

        if (isNetAvailable()) {
            OkHttpClient myclient = new OkHttpClient();
            Request myrequest = new Request.Builder()
                    .url(apiURL)
                    .build();

            Call mycall = myclient.newCall(myrequest);
            mycall.enqueue(new Callback() {
                @Override
                public void onFailure(Call mycall, IOException e) {
                    alertError();
                }

                @Override
                public void onResponse(Call mycall, Response myresponse) throws IOException {
                    String jSonData = myresponse.body().string();

                    if (myresponse.isSuccessful()) {
                        try {
                            JSONObject total = new JSONObject(jSonData);

                            JSONObject coords = ((JSONArray) total
                                    .get("results"))
                                    .getJSONObject(0)
                                    .getJSONObject("geometry")
                                    .getJSONObject("location");

                            JSONObject name = total
                                    .getJSONArray("results")
                                    .getJSONObject(0);

                            mLocation.setCity(name.getString("formatted_address"));
                            mLocation.setLat(coords.getDouble("lat"));
                            mLocation.setLong(coords.getDouble("lng"));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startWeather();
                                }
                            });
                        } catch (JSONException e) {
                            alertError();
                        }
                    }
                    else {
                        alertError();
                    }
                }
            });
        }
        else {
            alertNetError();
        }
    }

    public void startWeather() {
        Intent myintent = new Intent(this, WeatherActivity.class);
        myintent.putExtra("city", mLocation.getCity());
        myintent.putExtra("latitude", mLocation.getLat());
        myintent.putExtra("longitude", mLocation.getLong());

        startActivity(myintent);
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
}
