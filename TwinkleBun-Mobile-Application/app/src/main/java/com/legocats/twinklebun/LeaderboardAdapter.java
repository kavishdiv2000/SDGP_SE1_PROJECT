package com.legocats.twinklebun;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private  List<LeaderboardItem> leaderboardItems;
    private  int currentUserIndex;

    public LeaderboardAdapter(List<LeaderboardItem> leaderboardItems, int currentUserIndex) {
        this.leaderboardItems = leaderboardItems;
        this.currentUserIndex = currentUserIndex;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardItem item = leaderboardItems.get(position);
        holder.tvName.setText(item.getName());
        holder.tvScore.setText(String.valueOf(item.getOverallScore()));

        if (position == currentUserIndex) {
            holder.tvRank.setText((position + 1) + ".");
            holder.itemView.setBackgroundColor(Color.CYAN);
            Log.d("cyan color", "onBindViewHolder: ");
        } else {
            holder.tvRank.setText((position + 1) + ".");
            holder.itemView.setBackgroundColor(Color.WHITE);
            Log.d("white color", "onBindViewHolder: ");
        }
    }

    @Override
    public int getItemCount() {
        return leaderboardItems != null ? leaderboardItems.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank, tvName, tvScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tv_rank);
            tvName = itemView.findViewById(R.id.tv_name);
            tvScore = itemView.findViewById(R.id.tv_score);
        }
    }
}