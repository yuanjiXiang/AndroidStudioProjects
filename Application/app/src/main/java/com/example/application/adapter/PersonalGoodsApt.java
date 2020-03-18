package com.example.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.example.application.R;
import com.example.application.bean.Trade;
import com.example.application.personal.Goods_detail;


import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PersonalGoodsApt extends RecyclerView.Adapter<RecyclerView.ViewHolder > {

    private List<Trade> data;
    private String objectId;
    Context context;

    public PersonalGoodsApt(List<Trade> data, Context context) {
        this.data = data;
        this.context = context;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView description, price;
        public ImageView imageView;
//        public Button update_btn, delete_btn;

        public RecyclerViewHolder(@NonNull View itemView, int view_type) {
            super(itemView);

            if (view_type == 0) {
                description = itemView.findViewById(R.id.p_goods_desc);
                price = itemView.findViewById(R.id.p_goods_price);
                imageView = itemView.findViewById(R.id.personal_goods_img);
//                delete_btn = itemView.findViewById(R.id.delete);
//                update_btn = itemView.findViewById(R.id.update);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_goods_item,parent,false);
        if (viewType == 0) {
            return new RecyclerViewHolder(view, 0);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        final Trade trade = data.get(position);

        recyclerViewHolder.description.setText(trade.getDescription());
        recyclerViewHolder.price.setText(trade.getPrice());

        Glide.with(holder.itemView)
                .load(trade.getImg_url())
                .override(500,500)
                .centerCrop()
                .into(recyclerViewHolder.imageView);

        // 点击每个商品，进入商品修改页
        recyclerViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将点击的商品信息传递到修改页
                Intent intent = new Intent(context, Goods_detail.class);
                intent.putExtra("id", trade.getObjectId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
