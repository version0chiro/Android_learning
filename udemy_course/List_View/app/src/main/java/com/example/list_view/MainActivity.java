package com.example.list_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.myListView);
        ArrayList<String> myfamily = new ArrayList<String>();
        myfamily.add("Sachin");
        myfamily.add("Rahul");
        myfamily.add("Deepa");
        myfamily.add("Rajesh");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myfamily);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // parent is the listAdapter, view is the thing that has been tapped , postion is the number of the tapped item, id is position but with different
                // value sometimes
                Log.i("name", myfamily.get(position));
                Toast.makeText(MainActivity.this, "Hello! "+myfamily.get(position), Toast.LENGTH_SHORT).show();
            }
        });


    }
}