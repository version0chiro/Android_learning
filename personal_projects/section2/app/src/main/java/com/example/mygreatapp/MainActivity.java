package com.example.mygreatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public  int Count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickFuntion(View view) {
        Count++;

        EditText myTextField = (EditText)findViewById(R.id.myTextField);
        EditText passwordTextField = (EditText)findViewById(R.id.password) ;
        AppCompatImageView image = (AppCompatImageView) findViewById(R.id.imageView);
        String userName = myTextField.getText().toString();
        Toast.makeText(MainActivity.this,"Hi there "+userName+"!!!",Toast.LENGTH_SHORT).show();
        Log.i("Info",myTextField.getText().toString());
        Log.i("Info",passwordTextField.getText().toString());
        if(Count%2==0){
            image.setImageResource(R.drawable.monkey);
        }
        else{
            image.setImageResource(R.drawable.man);
        }
    }
}