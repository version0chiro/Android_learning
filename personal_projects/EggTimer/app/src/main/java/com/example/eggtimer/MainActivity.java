package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    int time;

    public SeekBar timerBar;

    int defaultTime=90000;

    CountDownTimer Count;

    public String getTime(long seconds){
        long min= (TimeUnit.MILLISECONDS.toMinutes(seconds));
        long sec=(TimeUnit.MILLISECONDS.toSeconds(seconds-TimeUnit.MINUTES.toMillis(min)));
        String timeString= Long.toString(min)+":"+Long.toString(sec);
        Log.i("Time",timeString);
        return timeString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerBar = (SeekBar)findViewById(R.id.timerBar);

        TextView timer = (TextView) findViewById(R.id.timer);
        timer.setText("00:00");

        long timeMin=30000;

        long timeMax = 600000;
        timerBar.setMin((int) timeMin);

        timerBar.setMax((int) timeMax);


        timerBar.setProgress(defaultTime);



        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                time = progress;
                String timeString=getTime(progress);
                TextView timer = (TextView) findViewById(R.id.timer);
                timer.setText(timeString);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startTime(View view) {

        timerBar = (SeekBar)findViewById(R.id.timerBar);

        timerBar.setVisibility(View.GONE);


        Button stopButton = (Button) findViewById(R.id.button2);

        Button start = (Button) findViewById(R.id.startButton);

        stopButton.setVisibility(View.VISIBLE);

        start.setVisibility(View.GONE);

        Count=new CountDownTimer(time, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                String timeString=getTime(millisUntilFinished);
                TextView timer = (TextView) findViewById(R.id.timer);
                timer.setText(timeString);
                Log.i("Time",timeString);

            }

            @Override
            public void onFinish() {
                MediaPlayer mplayer = MediaPlayer.create(MainActivity.this,R.raw.airhorn);
                mplayer.start();
                timerBar.setVisibility(View.VISIBLE);

                timerBar.setProgress(defaultTime);

                stopButton.setVisibility(View.GONE);

                start.setVisibility(View.VISIBLE);

            }
        }.start();

    }

    public void Stop(View view) {

        if(Count != null){
            Count.cancel();
            timerBar.setVisibility(View.VISIBLE);

            timerBar.setProgress(defaultTime);

            Button stopButton = (Button) findViewById(R.id.button2);

            Button start = (Button) findViewById(R.id.startButton);


            stopButton.setVisibility(View.GONE);

            start.setVisibility(View.VISIBLE);

        }

    }
}