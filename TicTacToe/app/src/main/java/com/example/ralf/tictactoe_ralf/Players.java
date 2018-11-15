package com.example.ralf.tictactoe_ralf;



public class Players
{

    public String name;
    public int score;


    public Players()
    {

    }

    public Players(String name, int highScore)
    {
        this.name = name;
        this.score = highScore;
    }

    public String getName() {return name;}

    public int getScore() {return score;}

}
