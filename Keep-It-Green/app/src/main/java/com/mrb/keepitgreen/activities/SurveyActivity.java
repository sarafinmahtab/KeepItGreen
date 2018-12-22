package com.mrb.keepitgreen.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mrb.keepitgreen.R;
import com.mrb.keepitgreen.URLs;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        surveyHouseButton = findViewById(R.id.house_button);
        surveyDessertButton = findViewById(R.id.desert_button);
        surveyForestButton = findViewById(R.id.forest_button);
        surveyVillageButton = findViewById(R.id.village_button);
        surveyTownButton = findViewById(R.id.town_button);
        surveyWaterButton = findViewById(R.id.water_button);

        surveyHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSurvey(0);
            }
        });

        surveyDessertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSurvey(1);
            }
        });

        surveyForestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSurvey(2);
            }
        });

        surveyVillageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSurvey(3);
            }
        });

        surveyTownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSurvey(4);
            }
        });

        surveyWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSurvey(5);
            }
        });
    }

    private void secondSurvey(int surveyId) {

        final String accountId = getIntent().getStringExtra("accountId");

        Intent intent = new Intent(SurveyActivity.this, Survey2Activity.class);
        intent.putExtra("accountId", accountId);
        intent.putExtra("survey1", surveyId);
        startActivity(intent);
        finish();
    }

    private Button surveyHouseButton;
    private Button surveyDessertButton;
    private Button surveyForestButton;
    private Button surveyVillageButton;
    private Button surveyTownButton;
    private Button surveyWaterButton;
}
