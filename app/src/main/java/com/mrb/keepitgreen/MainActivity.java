package com.mrb.keepitgreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surveyImageButton = findViewById(R.id.location_survey_button);
        treeMapImageButton = findViewById(R.id.map_of_trees_button);
        realtimeDisasterUpdateImageButton = findViewById(R.id.realtime_disaster_update_button);

        leaderboardImageButton = findViewById(R.id.leaderboard_button);

        surveyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                startActivity(intent);
            }
        });

        treeMapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapOfTreesActivity.class);
                startActivity(intent);
            }
        });

        realtimeDisasterUpdateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapTrackerActivity.class);
                startActivity(intent);
            }
        });

        leaderboardImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });
    }


    private ImageButton surveyImageButton;
    private ImageButton treeMapImageButton;
    private ImageButton realtimeDisasterUpdateImageButton;

    private ImageButton leaderboardImageButton;
}
