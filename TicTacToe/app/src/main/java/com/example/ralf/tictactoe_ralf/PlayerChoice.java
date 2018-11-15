package com.example.ralf.tictactoe_ralf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.media.MediaPlayer;

public class PlayerChoice extends AppCompatActivity
{

    MediaPlayer playerchoice;

    //Clears user input
    private void clearInput()
    {
        ((EditText) findViewById(R.id.player1_input)).setText("");
        ((EditText) findViewById(R.id.player2_input)).setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_choice);
        playerchoice = MediaPlayer.create(this,R.raw.playerchoice);
        playerchoice.setLooping(true);
        playerchoice.start();

        final EditText firstPlayer = (EditText) findViewById(R.id.player1_input);
        final EditText secondPlayer = (EditText) findViewById(R.id.player2_input);
        Button play = (Button) findViewById(R.id.play);

        play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String p1 = firstPlayer.getText().toString();
                String p2 = secondPlayer.getText().toString();
                //Checks if there is any input
                if(p1.equals("") || p2.equals(""))
                {
                    Toast.makeText(PlayerChoice.this,"You must enter something!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Gets player inputs and displays it in the game
                    Intent intent = new Intent(PlayerChoice.this,Game.class);
                    intent.putExtra("p1", firstPlayer.getText().toString());
                    intent.putExtra("p2", secondPlayer.getText().toString());
                    //Starts the game
                    startActivity(intent);
                    clearInput();
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        playerchoice.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        playerchoice.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        playerchoice.start();
    }

    public void playGame(View view) {
    }
}