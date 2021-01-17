package com.example.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // first way
//        Handler handler = new Handler();
//
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                //insert code to be run every x seconds
//                Toast.makeText(MainActivity.this, "Time ho gaya bc", Toast.LENGTH_SHORT).show();
//
//                handler.postDelayed(this,1000);
//            }
//        };
//
//        handler.post(run);


        // second way countdown timer
        new CountDownTimer(10000,1000){
            public void onTick(long millisecondsUntilDone){
                Log.i("Seconda left",String.valueOf(millisecondsUntilDone/1000));
            }

            @Override
            public void onFinish() {

                Log.i("Done!","Countdown finished");

            }
        }.start();
    }

}