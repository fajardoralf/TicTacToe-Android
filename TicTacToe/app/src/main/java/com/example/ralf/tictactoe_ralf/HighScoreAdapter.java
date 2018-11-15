package com.example.ralf.tictactoe_ralf;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HighScoreAdapter extends ArrayAdapter<Players> {
    private Activity context;
    private List<Players> players;

    public HighScoreAdapter(Activity context, List<Players> highScoreList)
    {
        super(context,R.layout.highscore_adapter,highScoreList);
        this.context = context;
        this.players = highScoreList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.highscore_adapter, null, true);

        TextView txtName = (TextView) listViewItem.findViewById(R.id.p_name);
        TextView txtScore = (TextView) listViewItem.findViewById(R.id.p_score);


        Players player = players.get(position);

        txtName.setText(player.getName());
        txtScore.setText(String.valueOf(player.getScore()));

        return listViewItem;
    }
}
