package com.mrb.keepitgreen.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.mrb.keepitgreen.R;
import com.mrb.keepitgreen.URLs;

import java.util.ArrayList;
import java.util.List;

public class SurveyEndActivity extends AppCompatActivity {

    private String accountId;

    private String latitude;
    private String longitude;
    private String currentLocation;

    private int survey1Id;
    private int survey2Id;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_end);

        sharedPreferences = getSharedPreferences(URLs.sharedPreferenceName, MODE_PRIVATE);

        continueButton = findViewById(R.id.continue_button);

        accountId = getIntent().getStringExtra("accountId");
        survey1Id = getIntent().getIntExtra("survey1", 0);
        survey2Id = getIntent().getIntExtra("survey2", 0);

        latitude = sharedPreferences.getString("latitude", URLs.latitude);
        longitude = sharedPreferences.getString("longitude", URLs.longitude);
        currentLocation = sharedPreferences.getString("current_location", "Dhaka");

        Log.d("accountId", accountId);
        Log.d("survey1Id", String.valueOf(survey1Id));
        Log.d("survey2Id", String.valueOf(survey2Id));
        Log.d("latitude", latitude);
        Log.d("longitude", longitude);
        Log.d("currentLocation", currentLocation);

        /*

        Polygon checking

        List<LatLng> latLngList = new ArrayList<>();
        latLngList.add(new LatLng(23.787220, 90.414294));
        latLngList.add(new LatLng(23.788119, 90.421071));
        latLngList.add(new LatLng(23.765215, 90.398964));
        latLngList.add(new LatLng(23.768318, 90.429420));

        boolean contains = PolyUtil.containsLocation(
                new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)),
                latLngList,
                false);


        List<LatLng> latLngList2 = new ArrayList<>();
        latLngList.add(new LatLng(24.965300, 91.562075));
        latLngList.add(new LatLng(24.857953, 92.128065));
        latLngList.add(new LatLng(24.979284, 91.934406));
        latLngList.add(new LatLng(24.759047, 91.872652));

        boolean contains2 = PolyUtil.containsLocation(
                new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)),
                latLngList2,
                false);

        Log.d("contain", String.valueOf(contains));
        Log.d("contain", String.valueOf(contains2));
        */
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Button continueButton;
}
