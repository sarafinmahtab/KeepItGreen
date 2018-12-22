package com.mrb.keepitgreen.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mrb.keepitgreen.LocationPermissionUtils;
import com.mrb.keepitgreen.R;
import com.mrb.keepitgreen.URLs;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final long UPDATE_INTERVAL = 10 * 1000;
    private static final long FASTEST_INTERVAL = 2000;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationClient;

    private String latitude;
    private String longitude;
    private String currentLocation;

    private String provider;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int networkState = getIntent().getIntExtra("networkState", 0);
        if (networkState == 1) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (networkState == 2) {
            provider = LocationManager.NETWORK_PROVIDER;
        }

        surveyImageButton = findViewById(R.id.location_survey_button);
        treeMapImageButton = findViewById(R.id.map_of_trees_button);
        realtimeDisasterUpdateImageButton = findViewById(R.id.realtime_disaster_update_button);

        leaderboardImageButton = findViewById(R.id.leaderboard_button);

        sharedPreferences = getSharedPreferences(URLs.sharedPreferenceName, MODE_PRIVATE);
        latitude = sharedPreferences.getString("latitude", URLs.latitude);
        longitude = sharedPreferences.getString("longitude", URLs.longitude);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkLocationPermission();
        } else {
            startLocationUpdates();
        }

        surveyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SurveyActivity.class);
                intent.putExtra("accountId", getIntent().getStringExtra("accountId"));
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

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //Permission Check
            LocationPermissionUtils.requestPermission(MainActivity.this);
        } else {
            startLocationUpdates();
        }
    }

    protected void startLocationUpdates() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //location = locationResult.getLastLocation();
                Location location = locationResult.getLocations().get(0);
                getLatLong(location);
            }

            @Override
            public void onLocationAvailability(LocationAvailability availability) {
                boolean isLocationAvailable = availability.isLocationAvailable();

                Log.d("LocationAvailability", String.valueOf(isLocationAvailable));
            }
        };

        // Create the location request to start receiving updates
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setNumUpdates(1);

        if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }else{
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(MainActivity.this);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)

        Task<LocationSettingsResponse> locationResponse = settingsClient.checkLocationSettings(locationSettingsRequest);
        locationResponse.addOnSuccessListener(MainActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,
                        Looper.myLooper());
            }
        });

        locationResponse.addOnFailureListener(MainActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("onFailure", "RESOLUTION_REQUIRED");
                        getLatLong(null);
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("onFailure", "SETTINGS_CHANGE_UNAVAILABLE");
                        getLatLong(null);
                        break;
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            getLatLong(location);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if(fusedLocationClient != null) {
            startLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode != LocationPermissionUtils.REQUEST_CODE) {
            return;
        }

        if (LocationPermissionUtils.isPermissionGranted(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, grantResults)) {
            startLocationUpdates();
        } else {
            Toast.makeText(MainActivity.this, "Permission Denied!!", Toast.LENGTH_LONG).show();
            System.exit(0);
        }
    }


    public void getLatLong(Location location) {

        if(location != null) {

            double lat = location.getLatitude();
            double lng = location.getLongitude();

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(lat, lng, 1);  // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                currentLocation = addresses.get(0).getLocality();
            } catch (IOException e) {
                Log.d("IOException", e.getMessage());
                e.printStackTrace();
            }

            latitude = String.valueOf(lat);
            longitude = String.valueOf(lng);

            editor = sharedPreferences.edit();
            editor.putString("latitude", latitude);
            editor.putString("longitude", longitude);
            editor.putString("current_location", currentLocation);
            editor.apply();
        }

        Log.d("Lat", latitude);
        Log.d("Long", longitude);
        Log.d("current_location", currentLocation);
    }

    private ImageButton surveyImageButton;
    private ImageButton treeMapImageButton;
    private ImageButton realtimeDisasterUpdateImageButton;

    private ImageButton leaderboardImageButton;
}
