package com.example.application.personal;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.application.R;
import com.example.application.bean.Trade;
import com.example.application.bean.User;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class Goods_detail extends AppCompatActivity {

    private LinearLayout linearLayout;
    private EditText goods_desc, goods_price;
    private Button submit_btn, delete_btn;
    private ImageView goods_pic;
    PictureBean pictureBean;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        // 获取界面组件
        initUI();

        // 获取传递过来的id,并渲染新页面
        final BmobQuery<Trade> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(id, new QueryListener<Trade>() {
            @Override
            public void done(Trade trade, BmobException e) {
                if (e == null) {
                    // 渲染
                    Glide.with(getApplicationContext())
                            .load(trade.getImg_url())
                            .into(goods_pic);
                    goods_desc.setText(trade.getDescription());
                    goods_price.setText(trade.getPrice());
                }
            }
        });

        final boolean[] is_change_pic = {false};
        goods_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(Goods_detail.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true, 500, 500, 1, 1);
                is_change_pic[0] = true;
            }
        });

       // 提交修改
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trade trade = new Trade();
                trade.setDescription(goods_desc.getText().toString());
                trade.setPrice(goods_price.getText().toString());
                trade.setUser(BmobUser.getCurrentUser(User.class));

                // 如果图片更改了
                if (is_change_pic[0]) {
                    // 先上传图片，获取图片地址
                    BmobFile bmobFile = new BmobFile(new File(pictureBean.getPath()));
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                        }
                    });
                    trade.setImg_url(bmobFile.getFileUrl());
                }

                // 提交
                trade.update(id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // 删除商品
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trade trade = new Trade();
                trade.setObjectId(id);
                trade.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            linearLayout.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    // 图片选择回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                if (pictureBean.isCut()) {
                    goods_pic.setImageBitmap(BitmapFactory.decodeFile(pictureBean.getPath()));
                } else {
                    goods_pic.setImageURI(pictureBean.getUri());
                }
            }
        }
    }

    private void initUI() {
        goods_desc = findViewById(R.id.p_goods_desc);
        goods_price = findViewById(R.id.p_goods_price);
        submit_btn = findViewById(R.id.update);
        delete_btn = findViewById(R.id.delete);
        goods_pic = findViewById(R.id.personal_goods_img);
        linearLayout = findViewById(R.id.goods_detail);
    }
}
