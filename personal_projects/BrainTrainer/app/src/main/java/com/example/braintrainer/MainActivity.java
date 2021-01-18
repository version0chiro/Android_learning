package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    boolean GameRunning=false;
    TextView timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchView(){
        if(GameRunning){
        Button startButton = (Button) findViewById(R.id.Start);
        startButton.setVisibility(View.GONE);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setVisibility(View.VISIBLE);
        timer = (TextView) findViewById(R.id.timer);
        timer.setVisibility(View.VISIBLE);
        TextView score = (TextView) findViewById(R.id.score);
        score.setVisibility(View.VISIBLE);
        TextView sum = (TextView) findViewById(R.id.sum);
        sum.setVisibility(View.VISIBLE);
        TextView answer = (TextView) findViewById(R.id.answer);
        answer.setVisibility(View.VISIBLE);


    }
        else{
            Button startButton = (Button) findViewById(R.id.Start);            startButton.setVisibility(View.VISIBLE);
            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
            gridLayout.setVisibility(View.GONE);
            TextView timer = (TextView) findViewById(R.id.timer);
            timer.setVisibility(View.GONE);
            TextView score = (TextView) findViewById(R.id.score);
            score.setVisibility(View.GONE);
            TextView sum = (TextView) findViewById(R.id.sum);
            sum.setVisibility(View.GONE);
            TextView answer = (TextView) findViewById(R.id.answer);
            answer.setVisibility(View.GONE);
        }
    }
    public void startGame(View view) {
        GameRunning = true;
        switchView();

        new CountDownTimer(30000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText((int) TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                GameRunning=false;
                switchView();
            }
        }.start();

    }
}