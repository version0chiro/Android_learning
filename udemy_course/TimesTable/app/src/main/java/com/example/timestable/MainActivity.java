package com.example.timestable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    public void printTable(int p){
        ListView listView = (ListView) findViewById(R.id.myTable);
        try {
            ArrayList<Integer> timeTable = new ArrayList<>();
            for(int i=1;i<11;i++){
                timeTable.add(i*p);
            }
//            System.out.println(timeTable);
            ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,timeTable);
            listView.setAdapter(arrayAdapter);

        }
        catch (Exception e){
            Log.i("catch",e.toString());
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.myTable);
        SeekBar numberBar = (SeekBar) findViewById(R.id.numberBar);

        numberBar.setMin(1);
        numberBar.setMax(20);



        numberBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("numbers for table",Integer.toString(progress));
                printTable(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}