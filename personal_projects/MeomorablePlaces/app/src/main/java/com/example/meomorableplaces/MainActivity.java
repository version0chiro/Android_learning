package com.example.meomorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static  ArrayList<String> places = new ArrayList<>();
    static  ArrayList<LatLng> locations = new ArrayList<>();
    static  ArrayAdapter arrayAdapter;
    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);

        sharedPreferences  = this.getSharedPreferences("com.example.meomorableplaces", Context.MODE_PRIVATE);


        ArrayList<String> lattitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();
        places.clear();
        lattitudes.clear();
        longitudes.clear();
        locations.clear();
        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String >())));
            lattitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes",ObjectSerializer.serialize(new ArrayList<String >())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes",ObjectSerializer.serialize(new ArrayList<String >())));


        } catch (IOException e) {
            e.printStackTrace();
        }

        if (places.size()>0 && lattitudes.size()>0 && longitudes.size()>0){
            Log.i("if","entering here");
            if(places.size()==lattitudes.size() && lattitudes.size()==longitudes.size()){
                for(int i =0 ; i<lattitudes.size();i++){
                    locations.add(new LatLng(Double.parseDouble(lattitudes.get(i)),Double.parseDouble(longitudes.get(i))));
                }
            }
        }
        else{
            Log.i("if2","entering here 2");
            places.add("Add a new place...");
            locations.add(new LatLng(0,0));

        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,places);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, Integer.toString(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("placeNumber",position);

                startActivity(intent);
            }
        });
    }
}