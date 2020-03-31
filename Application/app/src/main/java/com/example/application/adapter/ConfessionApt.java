package com.example.application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.bean.Confession;

import java.util.List;

public class ConfessionApt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Confession> confessionList;

    public ConfessionApt(List<Confession> confessionList) {
        this.confessionList = confessionList;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confess_item_layout,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        final Confession confession = confessionList.get(position);

        recyclerViewHolder.content.setText(confession.getContent());
    }

    @Override
    public int getItemCount() {
        return confessionList.size();
    }
}
