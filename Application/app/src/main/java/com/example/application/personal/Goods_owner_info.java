package com.example.application.personal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.application.R;
import com.example.application.bean.Trade;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class Goods_owner_info extends AppCompatActivity {
    private ImageView goods_pic;
    private TextView p_goods_desc, p_goods_price, goods_owner, goods_owner_phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_owner_info);

        // 初始化物品组件
        initUI();

        // 初始化用户名为空，用来记录商品的owner，并通过owner 在user表中查询其相关信息
        final String[] owner = {""};

        // 获取传递过来的商品id，以此来搜索商品信息
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        // 查询商品相关详细
        final BmobQuery<Trade> bmobQuery1 = new BmobQuery<>();
        bmobQuery1.include("user[mobilePhoneNumber]");
        bmobQuery1.getObject(id, new QueryListener<Trade>() {
            @Override
            public void done(Trade trade, BmobException e) {
                if (e == null) {
                    Glide.with(getApplicationContext())
                            .load(trade.getImg_url())
                            .override(500, 500)
                            .into(goods_pic);
                    p_goods_desc.setText(trade.getDescription());
                    p_goods_price.setText(trade.getPrice());
                    goods_owner.setText(trade.getOwner());
                    goods_owner_phone.setText(trade.getUser().getMobilePhoneNumber());
                }
            }
        });
    }
    private void initUI() {
        goods_pic = findViewById(R.id.personal_goods_img);
        p_goods_desc = findViewById(R.id.p_goods_desc);
        p_goods_price = findViewById(R.id.p_goods_price);
        goods_owner = findViewById(R.id.goods_owner);
        goods_owner_phone = findViewById(R.id.goods_owner_phone);
    }
}
