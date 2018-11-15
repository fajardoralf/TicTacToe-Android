package com.example.ralf.tictactoe_ralf;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighScore extends AppCompatActivity
{
    private ListView lvItem;

    List<Players> listScore;

    DatabaseReference dataReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childReference = dataReference.child("HighScore");

    private Button goBack;
    private Game game;

    MediaPlayer highscore;
    //Go back from highscore to game activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

        //Starts theme song for activity
        highscore = MediaPlayer.create(HighScore.this,R.raw.highscore);
        highscore.setLooping(true);
        highscore.start();

        game = new Game();

        listScore = new ArrayList<>();
        lvItem = (ListView) findViewById(R.id.list);

        //Back to game
        goBack = (Button) findViewById(R.id.back);
        goBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        addToListView();
        highscore.start();
    }
    //Sets pause on music when changing activity
    @Override
    protected void onPause()
    {
        super.onPause();
        highscore.pause();
    }
    //Resumes song when back in activity
    @Override
    protected void onResume()
    {
        super.onResume();
        highscore.start();
    }

    //Enters Name Screen
    public void newGame_highScore (View view)
    {
        Intent newGame = new Intent(this, PlayerChoice.class);
        startActivity(newGame);
    }

    //Sorting players, player with the highest score sits on top.
    private void addToListView()
    {
        childReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot)
            {
                listScore.clear();
                for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Players playerScore = ds.getValue(Players.class);
                    listScore.add(playerScore);
                }

                HighScoreAdapter adapter = new HighScoreAdapter(HighScore.this, listScore);
                Collections.sort(listScore, new Comparator<Players>() {
                    @Override
                    public int compare(Players o1, Players o2)
                    {
                        return o2.score - o1.score;
                    }
                });
                lvItem.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }
}
