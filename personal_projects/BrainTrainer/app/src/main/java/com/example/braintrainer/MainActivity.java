package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    boolean GameRunning=false;
    TextView timer;
    TextView sum;
    TextView option1;
    TextView option2;
    int totalAsked=0;
    TextView option3;
    TextView option4;
    int userScore;
    TextView score;
    int rightAnswer;
    Random rand;
    TextView answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rand = new Random();
        option1 = (TextView)findViewById(R.id.option1);
        option2 = (TextView)findViewById(R.id.option2);
        option3 = (TextView)findViewById(R.id.option3);
        option4 = (TextView)findViewById(R.id.option4);


    }

    public void generateSum(){
        rightAnswer = rand.nextInt(100);
        int firstDigit = rand.nextInt(rightAnswer);
        int secondDigit = rightAnswer - firstDigit;
        String sumString = Integer.toString(firstDigit) + "+" + Integer.toString(secondDigit);
        int rightOption = rand.nextInt(4)+1;
        List<Integer> options = new ArrayList<Integer>();
        for (int i =1;i<5;i++){
            if(i==rightOption){
                options.add(rightAnswer);
            }
            else{

                options.add(rand.nextInt(100));
            }
        }
        option1.setText(Integer.toString(options.get(0)));
        option2.setText(Integer.toString(options.get(1)));
        option3.setText(Integer.toString(options.get(2)));
        option4.setText(Integer.toString(options.get(3)));
        sum.setText(sumString);
    }

    public void switchView(){
        if(GameRunning){
        Button startButton = (Button) findViewById(R.id.Start);
        startButton.setVisibility(View.GONE);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setVisibility(View.VISIBLE);
        timer = (TextView) findViewById(R.id.timer);
        timer.setVisibility(View.VISIBLE);
        score = (TextView) findViewById(R.id.score);
        score.setVisibility(View.VISIBLE);
        sum = (TextView) findViewById(R.id.sum);
        sum.setVisibility(View.VISIBLE);
        answer = (TextView) findViewById(R.id.answer);
        answer.setVisibility(View.VISIBLE);


    }
        else{
            Button startButton = (Button) findViewById(R.id.Start);            startButton.setVisibility(View.VISIBLE);
            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
            gridLayout.setVisibility(View.GONE);
            timer = (TextView) findViewById(R.id.timer);
            timer.setVisibility(View.GONE);
            score = (TextView) findViewById(R.id.score);
            score.setVisibility(View.GONE);
            sum = (TextView) findViewById(R.id.sum);
            sum.setVisibility(View.GONE);
            answer = (TextView) findViewById(R.id.answer);
            answer.setVisibility(View.GONE);
        }
    }

    public  void updateScore(boolean status){
        totalAsked++;
        if (status==true){
            userScore++;
        }
        String scoreString = Integer.toString(userScore) +"/"+Integer.toString(totalAsked);
        score.setText(scoreString);

    }
    public void startGame(View view) {
        GameRunning = true;
        switchView();

        new CountDownTimer(30000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(Integer.toString((int) TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));
            }

            @Override
            public void onFinish() {
                GameRunning=false;
                switchView();
            }
        }.start();

        generateSum();



    }

    public void submitAnswer(View view) {
        TextView submittedAnswerView = (TextView) view;
        Integer submittedAnswer = Integer.parseInt((String) submittedAnswerView.getText());
        updateScore(submittedAnswer==rightAnswer);
        if(submittedAnswer==rightAnswer){
            Toast.makeText(this, "Right Answer", Toast.LENGTH_SHORT).show();
            System.out.println("Right Answer");
            answer.setText("Right Answer");
        }
        else {
            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
            System.out.println("Wrong Answer");
            answer.setText("Wrong Answer");
        }
        generateSum();

    }
}