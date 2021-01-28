package com.example.multiactivity;

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
        final ArrayList<String> friends  = new ArrayList<>();
        friends.add("Monica");
        friends.add("Chandler");
        friends.add("Ross");
        friends.add("Pheobe");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,friends);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);

                intent.putExtra("name",friends.get(position));

                startActivity(intent);
            }
        });
    }

    public void toSecondActivity(View view) {
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);

        intent.putExtra("username","sachin");

        startActivity(intent);
    }
}