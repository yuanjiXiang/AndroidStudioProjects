package com.example.application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.bean.Info;

import java.util.List;


public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Info> data;

    public InfoAdapter(List<Info> data) {
        this.data = data;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView title, content, author, time;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            author = itemView.findViewById(R.id.author);

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new RecyclerViewHolder(view); // 返回加载完成的item
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final InfoAdapter.RecyclerViewHolder recyclerViewHolder = (InfoAdapter.RecyclerViewHolder) holder;
        final Info info = data.get(position);

        recyclerViewHolder.title.setText(info.getTitle());
        recyclerViewHolder.content.setText(info.getContent());
        recyclerViewHolder.author.setText(info.getAuthor());
        recyclerViewHolder.time.setText(info.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
