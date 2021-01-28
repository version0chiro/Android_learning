package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        ArrayList<String> myFavLocataions = new ArrayList<>();



        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,myFavLocataions);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String optionSelected = myFavLocataions.get(position);
                if(optionSelected== "Add a new place..."){
                    Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                    intent.putExtra("flag",0);
                    startActivity(intent);

                }
                else{
                    Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                    intent.putExtra("flag",1);
                    intent.putExtra("name",optionSelected);
                    startActivity(intent);


                }
            }
        });



    }
}