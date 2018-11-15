package com.example.ralf.tictactoe_ralf;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    MediaPlayer main;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main  = MediaPlayer.create(MainActivity.this,R.raw.main);
        main.setLooping(true);
        main.start();
    }

    public void player_choice (View view)
    {
        Intent player_choice = new Intent(this, PlayerChoice.class);
        startActivity(player_choice);
    }

    public void leade (View view)
    {
        Intent leadeboard = new Intent(this, HighScore.class);
        startActivity(leadeboard);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        main.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        main.start();
    }
}