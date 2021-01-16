package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0 is yellow ,1 is red
    int activePlayer = 0;
    String[] players = {"Yellow","Red"};
    int[] gameState= {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void playAgain(View view){
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;
        for (int i=0;i<gameState.length;i++){
            gameState[i]=2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i =0 ;i<gridLayout.getChildCount()-1;i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    public  void dropin(View view){
        ImageView counter = (ImageView) view;
        String tag =  counter.getTag().toString();
        int tappedCounter = Integer.parseInt(tag);
        if (gameState[tappedCounter]==2) {
            gameState[tappedCounter]=activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);

                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;

            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            for (int[] winningPostion:winningPositions){
                if (gameState[winningPostion[0]]== gameState[winningPostion[1]] && gameState[winningPostion[1]]== gameState[winningPostion[2]] && gameState[winningPostion[0]]!=2){
                    Toast.makeText(this, "Player"+activePlayer+"has won!", Toast.LENGTH_LONG).show();
                    System.out.println("Player "+activePlayer+" has won!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
                    TextView winnerMessage = (TextView) findViewById(R.id.winMessage);
                    winnerMessage.setText("Player "+players[activePlayer]+" has won!");
                    layout.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}