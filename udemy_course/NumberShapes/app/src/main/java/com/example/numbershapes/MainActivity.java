package com.example.numbershapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    class NumberShape{
        int number;

        public boolean isSquare(){
            double root = Math.sqrt(number);
            if(root%1==0){
                return true;
            }
            else {
                return false;
            }
        }

        public boolean isTriangular(){
            int x=1;
            int triangularNumber=1;

            while(triangularNumber<number){
                x++;
                triangularNumber = triangularNumber +x;
            }

            if (triangularNumber==number){
                return true;
            }
            else{
                return false;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Check(View view) {
        EditText userNumber = (EditText) findViewById(R.id.editTextTextPersonName);
        try {
            int number=Integer.parseInt(userNumber.getText().toString());

            //        Toast.makeText(this, Integer.toString(number), Toast.LENGTH_SHORT).show();
            NumberShape mynumber = new NumberShape();
            mynumber.number=number;
            if(mynumber.isSquare()  && mynumber.isTriangular()){
                Toast.makeText(this, "Number is square and triangular", Toast.LENGTH_SHORT).show();

            }
            else if(mynumber.isSquare()){
                Toast.makeText(this, "Number is Square", Toast.LENGTH_SHORT).show();
            }
            else if(mynumber.isTriangular()){
                Toast.makeText(this, "Number is Triangular", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(this, "Number is not square nor triangular", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }


    }
}