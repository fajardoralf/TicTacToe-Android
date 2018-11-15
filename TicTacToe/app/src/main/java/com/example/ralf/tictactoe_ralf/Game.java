package com.example.ralf.tictactoe_ralf;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Game extends AppCompatActivity
{
    TextView text;
    private Board board = null;
    private int counter = 0, x = 0, y = 0;
    private String player1 = "X", player2 = "O";
    private boolean isOver = false;

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childRef = dbRef.child("HighScore");

    MediaPlayer theme;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        theme =  MediaPlayer.create(this,R.raw.game);
        theme.setLooping(true);
        theme.start();

        //creates an instance of board
        board = new Board();

        //Get text from Player Input
        TextView tv1 = (TextView) findViewById(R.id.player1);
        TextView tv2 = (TextView) findViewById(R.id.player2);
        tv1.setText(getIntent().getExtras().getString("p1") + " " + "(X)");
        tv2.setText(getIntent().getExtras().getString("p2") + " " + "(O)");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        theme.start();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        theme.pause();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        theme.start();
    }

    //Checks if there is any available moves and checks if there is empty field
    //Ensures that player cannot type when there is a winner
    public void click(View view)
    {
        TextView v = (TextView) findViewById(view.getId());
        String available = (String) v.getText();
        if(available.equals("") && !isOver)
        {
            switch (v.getId()) {
                case R.id.view1:
                    x = 0;
                    y = 0;
                    break;
                case R.id.view2:
                    x = 0;
                    y = 1;
                    break;
                case R.id.view3:
                    x = 0;
                    y = 2;
                    break;
                case R.id.view4:
                    x = 1;
                    y = 0;
                    break;
                case R.id.view5:
                    x = 1;
                    y = 1;
                    break;
                case R.id.view6:
                    x = 1;
                    y = 2;
                    break;
                case R.id.view7:
                    x = 2;
                    y = 0;
                    break;
                case R.id.view8:
                    x = 2;
                    y = 1;
                    break;
                case R.id.view9:
                    x = 2;
                    y = 2;
                    break;
            }

            //Changes players (X allways starts first)
            if (v.getText() == "" && counter % 2 == 0)
            {
                playerMove(true);
                v.setText(player1);
                v.setTextColor(getResources().getColorStateList(R.color.black));
                counter++;
                isOver = checkEnd(player1);
            }
            else if (v.getText() == "" && counter % 2 == 1)
            {
                playerMove(false);
                v.setTextColor(getResources().getColorStateList(R.color.green));
                v.setText(player2);
                counter++;
                isOver = checkEnd(player2);
            }
        }
    }

    //Player move marker
    public void playerMove(Boolean move)
    {
        if(move)
        {
            board.mark(x, y, player1);
        }
        else
        {
            board.mark(x, y, player2);
        }

    }

    //checks if there is any winner
    private boolean checkEnd(String player)
    {
        if (board.wins(player))
        {
            winner(true, player);
            return true;
        }

        else if (counter >= 9)
        {
            winner(false, player);
            return true;
        }
        return false;
    }
    //announces winner and displays name of the winner
    private void winner(boolean end, String player)
    {
      String playerShow;

        if (end)
        {
            if (player.equals("X"))
            {
                player = getPlayer(true);

                if(player.contains("(X)"))
                {
                    player = player.substring(0, player.indexOf("(X)"));
                    saveHighScore(player);
                }
                playerShow = getPlayer(true) + " " + "Player 1 wins! ";
            }
            else
            {
                player = getPlayer(false);
                if(player.contains("O"))
                {
                    player = player.substring(0, player.indexOf("(O)"));
                    saveHighScore(player);
                }
                playerShow = getPlayer(false) + " " + "Player 2 wins! ";
            }
        }
        else
        {
            playerShow = "It's a draw!";
        }
        Context context = getApplicationContext();
        Toast toast_p1 = Toast.makeText(context, playerShow, Toast.LENGTH_LONG);
        toast_p1.show();
    }

    //Gets player name
    public String getPlayer(Boolean player)
    {
            if(player)
            {
                text = (TextView) findViewById(R.id.player1);
                String player_1_name = text.getText().toString();
                return player_1_name;
            }
            else
            {
                text = (TextView) findViewById(R.id.player2);
                String player_2_name = text.getText().toString();
                return player_2_name;
            }
    }

    //Cleans the board (Reset button)
    public void clean(View view)
    {
        int[] list = {R.id.view1,R.id.view2,R.id.view3,R.id.view4,R.id.view5,
                R.id.view6,R.id.view7,R.id.view8,R.id.view9};
        TextView cell;
        for(int item : list )
        {
            cell = (TextView) findViewById(item);
            cell.setText("");
        }
        isOver = false;
        counter = 0;
        board.clear();
    }

    //Saves Highscore to database(Firebase)
    private void saveHighScore(final String player)
    {
      final  Players highScores = new Players(player,1);
        childRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                boolean exist = false;
                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    Map<String, Object> model = (Map<String, Object>) child.getValue();
                    if (model.get("name").equals(player))
                    {
                        exist = true;
                        break;
                    }
                }
                    if (exist)
                    {
                        Query scoreQuery = childRef.orderByChild("name").equalTo(player);
                        scoreQuery.addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot)
                            {
                                for (DataSnapshot child : dataSnapshot.getChildren())
                                {
                                    long value = (long) child.child("score").getValue();
                                    child.getRef().child("score").setValue(value + 1);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError)
                            {

                            }
                        });
                    }
                    else
                    {
                        childRef.push().setValue(highScores);
                    }
                }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    //Enters highscore screen
    public void highScore (View view)
    {
        Intent highScore = new Intent(this, HighScore.class);
        startActivity(highScore);
    }
    //Enters Name Screen
    public void newGame (View view)
    {
        Intent newGame = new Intent(this, PlayerChoice.class);
        startActivity(newGame);
    }

}

