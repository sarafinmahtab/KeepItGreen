package com.mrb.keepitgreen.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mrb.keepitgreen.R;

public class MapTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tracker);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
