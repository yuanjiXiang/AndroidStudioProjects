package com.example.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.application.R;
import com.example.application.bean.Trade;
import com.example.application.personal.Goods_owner_info;

import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {

    private Context context;
    private List<Trade> trade;

    public TradeAdapter(Context context, List<Trade> trade) {
        this.context = context;
        this.trade = trade;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView description, price;
        ImageView imageView;

        RecyclerViewHolder(@NonNull View itemView, int view_type) {
            super(itemView);

            if (view_type == 0) {
                description = itemView.findViewById(R.id.description);
                price = itemView.findViewById(R.id.price);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_item_layout,parent,false);

        return new RecyclerViewHolder(view, 0);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TradeAdapter.RecyclerViewHolder recyclerViewHolder = (TradeAdapter.RecyclerViewHolder) holder;
        final Trade trade1 = trade.get(position);

        recyclerViewHolder.price.setText(trade1.getPrice());
        recyclerViewHolder.description.setText(trade1.getDescription());

        Glide.with(holder.itemView)
                .load(trade1.getImg_url())
                .override(500,500)
                .centerCrop()
                .into(recyclerViewHolder.imageView);

        // 点击物品，进入物品详情页，包括物品发布者信息
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Goods_owner_info.class);
            intent.putExtra("id",trade1.getObjectId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return trade.size();
    }
}
