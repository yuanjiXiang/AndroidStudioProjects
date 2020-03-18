package com.example.application.adapter;

import android.content.Context;
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
    private Context context;
    private List<Info> data;

    public InfoAdapter(Context context, List<Info> data) {
        this.context = context;
        this.data = data;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView title, content, author, time;
        public TextView loading;

        public RecyclerViewHolder(@NonNull View itemView, int view_type) {
            super(itemView);
            if (view_type == 0) {
                title = itemView.findViewById(R.id.title);
                content = itemView.findViewById(R.id.content);
                time = itemView.findViewById(R.id.time);
                author = itemView.findViewById(R.id.author);
            } else {
                loading = itemView.findViewById(R.id.loading);
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        View loading_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_layout,parent,false);
        if (viewType  == 1) {
            return new RecyclerViewHolder(loading_view, 1); // 返回正在加载中
        } else {
            return new RecyclerViewHolder(view, 0); // 返回加载完成的item
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
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
