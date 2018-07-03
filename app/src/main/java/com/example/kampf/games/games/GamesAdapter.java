package com.example.kampf.games.games;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kampf.games.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder>{

    private List<Game> games = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_game, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Game game = games.get(position);
        Picasso.get().load(game.getImgUrl()).into(holder.ivPicture);
        holder.tvName.setText(game.getName());
        holder.tvDeck.setText(game.getDeck());

    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void addAll(List<Game> gamesToAdd) {

        games.addAll(gamesToAdd);
        notifyDataSetChanged();

    }

    public void replaceAll(List<Game> gamesToReplace) {

        games.clear();
        games.addAll(gamesToReplace);
        notifyDataSetChanged();

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPicture;
        TextView tvName;
        TextView tvDeck;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvName = itemView.findViewById(R.id.tvName);
            tvDeck = itemView.findViewById(R.id.tvDeck);
        }

    }

}
