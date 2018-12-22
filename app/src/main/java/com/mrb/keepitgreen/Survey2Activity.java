package com.mrb.keepitgreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Survey2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey2);

        uncountableButton = findViewById(R.id.uncountable_button);

        uncountableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Survey2Activity.this, SurveyEndActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Button uncountableButton;
}
