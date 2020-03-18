package com.example.application.personal;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.R;

import com.example.application.bean.Trade;
import com.example.application.bean.User;

import com.wildma.pictureselector.FileUtils;
import com.wildma.pictureselector.PictureSelector;
import com.wildma.pictureselector.PictureBean;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 创建新发布页面
 */

public class CreateGoods extends AppCompatActivity {

    private EditText goods_desc, goods_price;
    private Button submit_btn, select_goods_pic;
    PictureBean pictureBean;
    String pic_path;

    private void initUI() {
        goods_desc = findViewById(R.id.goods_desc);
        goods_price = findViewById(R.id.goods_price);
        submit_btn = findViewById(R.id.submit_goods);
        select_goods_pic = findViewById(R.id.select_goods_pic);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goods);

        initUI();

        // 选择图片
        select_goods_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(CreateGoods.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true, 500, 500, 1, 1);

                // 清理缓存
                FileUtils.deleteAllCacheImage(getApplicationContext());
            }
        });

        // 上传信息
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前用户信息
                final User user = BmobUser.getCurrentUser(User.class);
                final Trade trade = new Trade();

                // 上传图片，并获得图片url地址
                final BmobFile bmobFile = new BmobFile(new File(pic_path));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            // bmobFile.getFileUrl()--返回的上传文件的完整地址
                            // Toast.makeText(CreateGoods.this, "上传文件成功:" + bmobFile.getFileUrl(), Toast.LENGTH_LONG).show();
                            // 往Trade表添加信息
                            trade.setDescription(goods_desc.getText().toString());
                            trade.setPrice(goods_price.getText().toString());
                            trade.setImg_url(bmobFile.getFileUrl());
                            trade.setOwner(user.getUsername());

                            // 上传
                            trade.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(CreateGoods.this, "上传图片失败:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    // 显示图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imageView = findViewById(R.id.goods_img);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                assert pictureBean != null;
                if (pictureBean.isCut()) {
                    imageView.setImageBitmap(BitmapFactory.decodeFile(pictureBean.getPath()));
                    pic_path = pictureBean.getPath();
                } else {
                    imageView.setImageURI(pictureBean.getUri());
                }
            }
        }
    }

}
