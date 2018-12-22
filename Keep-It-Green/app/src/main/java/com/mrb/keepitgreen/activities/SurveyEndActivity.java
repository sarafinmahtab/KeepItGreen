package com.mrb.keepitgreen.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mrb.keepitgreen.R;
import com.mrb.keepitgreen.URLs;

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

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Button continueButton;
}
