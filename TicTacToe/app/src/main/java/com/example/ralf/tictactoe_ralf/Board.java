package com.example.ralf.tictactoe_ralf;


public class Board
{
    private String[][] board = new String[3][3];

    //Constuctor
    Board()
    {
        for (int x = 0; x < 3; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                board[x][y] = "";
            }
        }
    }

    //Clears board
    public void clear()
    {
        for (int x = 0; x < 3; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                board[x][y] = "";

            }
        }
    }

    //Marks board
    public void mark(int x ,int y, String mark)
    {
        if(board[x][y].equals(""))
        {
            board[x][y] = mark;
        }
    }

    //Determines who wins and checks the board
    public boolean wins(String player)
    {
        // Check Diagonals
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].equals("") && board[0][0].equals(player))

            return true;

        if (board[2][0].equals(board[1][1]) && board[2][0].equals(board[0][2]) && !board[2][0].equals("") && board[2][0].equals(player))

            return true;

        for (int i = 0; i < 3; i++)
        {

            // Check Rows
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals("") && board[i][0].equals(player))

                return true;

            // Check Columns
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].equals("") && board[0][i].equals(player))

                return true;
        }
        return false;
    }
}
