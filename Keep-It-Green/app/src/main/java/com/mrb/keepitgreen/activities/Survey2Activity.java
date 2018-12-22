package com.mrb.keepitgreen.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mrb.keepitgreen.R;
import com.mrb.keepitgreen.URLs;

public class Survey2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

        uncountableButton = findViewById(R.id.uncountable_button);
        moreThanTenButton = findViewById(R.id.more_than_ten_button);
        oneToTenButton = findViewById(R.id.one_to_ten_button);
        noTreeButton = findViewById(R.id.no_tree_button);

        uncountableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endSurvey(0);
            }
        });

        moreThanTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endSurvey(1);
            }
        });

        oneToTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endSurvey(2);
            }
        });

        noTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endSurvey(3);
            }
        });
    }


    private void endSurvey(int surveyId) {

        final String accountId = getIntent().getStringExtra("accountId");
        final int survey1Id = getIntent().getIntExtra("survey1", 0);

        Intent intent = new Intent(Survey2Activity.this, SurveyEndActivity.class);
        intent.putExtra("accountId", accountId);
        intent.putExtra("survey1", survey1Id);
        intent.putExtra("survey2", surveyId);
        startActivity(intent);
        finish();
    }


    private Button uncountableButton;
    private Button moreThanTenButton;
    private Button oneToTenButton;
    private Button noTreeButton;
}
