
package com.example.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    int flag;
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    float lan,lat;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        Intent intent = getIntent();

        flag = intent.getIntExtra("flag",0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        
        locationListener = location -> {
//                Toast.makeText(MapsActivity.this, location.toString(), Toast.LENGTH_SHORT).show();
            Log.i("Location Change ",location.toString());
            lat= (float) location.getLatitude();
            lan= (float) location.getLongitude();
            mMap.clear();
            // Add a marker in Sydney and move the camera
            mMap = googleMap;

            LatLng sydney = new LatLng(lat, lan);
            mMap.addMarker(new MarkerOptions().position(sydney).title("user location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

            try {
                List<Address> listAddress = geocoder.getFromLocation(lat,lan,1);
                if (listAddress!=null && listAddress.size()>0){
                    Log.i("place info",listAddress.get(0).toString());
                    Toast.makeText(MapsActivity.this, listAddress.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        };


        {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else {
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0,locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                lat= (float) lastKnownLocation.getLatitude();
                lan= (float) lastKnownLocation.getLongitude();
                // Add a marker in Sydney and move the camera
                mMap = googleMap;

                LatLng sydney = new LatLng(lat, lan);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(sydney).title("user location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            }
        }


    }
}