package com.example.application.adapter;

import android.content.Context;
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

import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {

    private Context context;
    private List<Trade> trade;

    public TradeAdapter(Context context, List<Trade> trade) {
        this.context = context;
        this.trade = trade;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView description, price;
        public ImageView imageView;

        public RecyclerViewHolder(@NonNull View itemView, int view_type) {
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
        if (viewType == 0) {
            return new RecyclerViewHolder(view, 0);
        } else {
            return null;
        }
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
    }

    @Override
    public int getItemCount() {
        return trade.size();
    }
}
