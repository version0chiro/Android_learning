package com.example.higherorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random rand = new Random();
    int randomNumber = rand.nextInt(10) +1;
    boolean checkFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Guess(View view) {

        Log.i("Number",Integer.toString(randomNumber));
        EditText numberText = (EditText) findViewById(R.id.Number);
        int number = Integer.parseInt(numberText.getText().toString());
        if(number==randomNumber){
            Toast.makeText(this, "You guessed correct!", Toast.LENGTH_SHORT).show();
            randomNumber = rand.nextInt(10)+1;
        }
        else if(number>randomNumber){
            Toast.makeText(this, "Lower", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Higher", Toast.LENGTH_SHORT).show();
        }
    }
}