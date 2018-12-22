package com.mrb.keepitgreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        surveyHouseButton = findViewById(R.id.house_button);

        surveyHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyActivity.this, Survey2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Button surveyHouseButton;
}
