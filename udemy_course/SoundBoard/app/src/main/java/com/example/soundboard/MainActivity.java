package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    MediaPlayer mplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mplayer=MediaPlayer.create(this,R.raw.heyheyhey);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void PlayMusic(View view) {
//        System.out.println(view.getId());
        switch (view.getId()){
            case R.id.button:
                System.out.println("button 1 was pressed");

                if (mplayer.isPlaying()){
                    mplayer.pause();
                }
                mplayer = MediaPlayer.create(this, R.raw.allmight);
                mplayer.start();
                break;
            case R.id.button2:
                System.out.println("button 2 was pressed");
                if (mplayer.isPlaying()){
                    mplayer.pause();
                }
                mplayer = MediaPlayer.create(this, R.raw.bakugo);
                mplayer.start();

                break;
            case R.id.button3:
                System.out.println("button 3 was pressed");
                if (mplayer.isPlaying()){
                    mplayer.pause();
                }
                mplayer = MediaPlayer.create(this, R.raw.shine);
                mplayer.start();

                break;
            case R.id.button4:
                System.out.println("button 4 was pressed");
                if (mplayer.isPlaying()){
                    mplayer.pause();
                }
                mplayer = MediaPlayer.create(this, R.raw.carry);
                mplayer.start();

                break;
            case R.id.button5:
                System.out.println("button 5 was pressed");
                if (mplayer.isPlaying()){
                    mplayer.pause();
                }
                mplayer = MediaPlayer.create(this, R.raw.godlike);
                mplayer.start();

                break;
            case R.id.button6:
                System.out.println("button 6 was pressed");
                if (mplayer.isPlaying()){
                    mplayer.pause();
                }
                mplayer = MediaPlayer.create(this, R.raw.smash);
                mplayer.start();

                break;
            case R.id.button7:
                System.out.println("button 7 was pressed");
                if (mplayer.isPlaying()){
                    mplayer.pause();
                }
                mplayer = MediaPlayer.create(this, R.raw.sharingan);
                mplayer.start();

                break;
            case R.id.button8:
                System.out.println("button 8 was pressed");
                if (mplayer.isPlaying()){
                    mplayer.pause();
                }
                mplayer = MediaPlayer.create(this, R.raw.heyheyhey);
                mplayer.start();

                break;
        }

    }
}