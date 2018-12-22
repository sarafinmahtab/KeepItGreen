package com.mrb.keepitgreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_end);

        continueButton = findViewById(R.id.continue_button);
        leaderboardButton = findViewById(R.id.leaderboard_btn);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyEndActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private Button continueButton;
    private Button leaderboardButton;
}
