package com.mrb.keepitgreen.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.mrb.keepitgreen.LocationPermissionUtils;
import com.mrb.keepitgreen.R;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 1000;
    private boolean isGPSEnabled, isNetworkEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null){
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.e("gps, network", String.valueOf(isGPSEnabled + "," + isNetworkEnabled));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkLocationPermission();
        } else {
            initHandler();
        }
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //Permission Check
            LocationPermissionUtils.requestPermission(SplashActivity.this);
        } else {
            initHandler();
        }
    }

    private void initHandler() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int networkState = 0;

                if (isGPSEnabled) {
                    networkState = 1;
                } else if (isNetworkEnabled) {
                    networkState = 2;
                }

                if (networkState == 0) {
                    LocationPermissionUtils.LocationSettingDialog.newInstance().show(getSupportFragmentManager(), "Setting");
                } else {

                    AccessToken accessToken = AccountKit.getCurrentAccessToken();

                    if (accessToken == null) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        intent.putExtra("networkState", networkState);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra("networkState", networkState);
                        intent.putExtra("accountId", accessToken.getAccountId());
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode != LocationPermissionUtils.REQUEST_CODE) {
            return;
        }

        if (LocationPermissionUtils.isPermissionGranted(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, grantResults)) {
            initHandler();
        } else {
            Toast.makeText(SplashActivity.this, "Permission Denied!!", Toast.LENGTH_LONG).show();
            System.exit(0);
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
