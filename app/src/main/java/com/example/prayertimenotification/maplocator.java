package com.example.prayertimenotification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import androidx.fragment.app.FragmentActivity;

public class maplocator extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap mGoogleMap;
    MapView mMapView;
    String data;
    public static String name;
    Double lat;
    Double lon;
    public static String parseddata;
    Button bt;
    Button bt2;
    Button mapbtn;
    Location currentlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_frag);
        bt = findViewById(R.id.btn_home2);
        bt2 = findViewById(R.id.btn_time2);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(maplocator.this, MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(), "loading home", Toast.LENGTH_SHORT).show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(maplocator.this, dailytime.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(), "Loading Time", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                   currentlocation=location;
                    Toast.makeText(getApplicationContext(),currentlocation.getLatitude()+""+currentlocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(maplocator.this);
                }
            }
        });
    }


  @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap=googleMap;

       LatLng latLng = new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
       MarkerOptions markerOptions =new MarkerOptions().position(latLng).title(" My current Location");
       googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
       googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
      googleMap.addMarker(markerOptions);


        googleMap.addMarker(new MarkerOptions().position(new LatLng(23.780733, 90.409166)).title("Gausul Azam Jame Masjid"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(23.779317, 90.409166)).title("Mohakhali TB Gate Jame Mosjid"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(23.782302, 90.413779)).title("Sapra Masjid"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(23.780837, 90.407747)).title("bracU"));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }

}

