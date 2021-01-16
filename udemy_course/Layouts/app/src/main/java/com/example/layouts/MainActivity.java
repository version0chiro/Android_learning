package com.example.layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        ImageView hinata = (ImageView)findViewById(R.id.hinata);

        //ImageView kageyama = (ImageView)findViewById(R.id.kageyama);

        hinata.setTranslationX(-2000f);
         */

    }

    public void fade(View view) {
        ImageView hinata = (ImageView)findViewById(R.id.hinata);

//        ImageView kageyama = (ImageView)findViewById(R.id.kageyama);
        if (flag==false) {
            hinata.animate().rotation(100f).setDuration(2000);
            hinata.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);
            flag = true;
        }
        else {
            hinata.animate().rotation(0f).setDuration(2000);
            hinata.animate().scaleX(1.0f).scaleY(1.0f).setDuration(2000);
            flag=false;
        }
        //kageyama.animate().alpha(1f).setDuration(2000);
//        kageyama.setClickable(false);

    }

    public void show(View view){
        Toast.makeText(this, "This message is custom", Toast.LENGTH_SHORT).show();
    }
}