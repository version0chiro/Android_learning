package com.example.hikerwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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


public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    float lan,lat;
    TextView latti,lon,acc,alt,add;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latti = (TextView)findViewById(R.id.lat);
        lon = findViewById(R.id.longi);
        acc = findViewById(R.id.acc);
        alt = findViewById(R.id.alt);
        add = findViewById(R.id.address);

        locationManager =  (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i("Location change",location.toString());
                lat = (float) location.getLatitude();
                lan= (float) location.getLongitude();

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> listAddress = geocoder.getFromLocation(lat,lan,1);
                    if(listAddress!=null && listAddress.size()>0){
//                        Toast.makeText(MainActivity.this, listAddress.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                        latti.setText("Lattitude:"+lat);
                        lon.setText("Longitude:"+lan);
                        acc.setText("Accuracy:"+location.getAccuracy());
                        alt.setText("Altitude:"+location.getAltitude());
                        add.setText("Address:"+listAddress.get(0).getAddressLine(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
            else{
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0,locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                lat = (float) lastKnownLocation.getLatitude();
                lan = (float) lastKnownLocation.getLongitude();

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> listAddress = geocoder.getFromLocation(lat,lan,1);
                    if(listAddress!=null && listAddress.size()>0){
//                        Toast.makeText(MainActivity.this, listAddress.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                        latti.setText("Lattitude:"+lat);
                        lon.setText("Longitude:"+lan);
                        acc.setText("Accuracy:"+lastKnownLocation.getAccuracy());
                        alt.setText("Altitude:"+lastKnownLocation.getAltitude());
                        add.setText("Address:"+listAddress.get(0).getAddressLine(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }


}