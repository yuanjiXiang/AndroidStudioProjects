package com.example.application.personal;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.application.MainActivity;
import com.example.application.R;
import com.example.application.bean.User;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.util.V;

/**
 * 个人信息修改页
 */

public class ModifyPersonalInfo extends AppCompatActivity {

    private EditText stu_num, phone, email;
    private Button submit_btn;
    private ImageView portrait;
    PictureBean pictureBean;
    private String default_portrait_url = "https://bmob-cdn-27983.bmobpay.com/2020/03/24/cf447d164086182c80f53ba88700c410.jpg";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_personal);

        initUI();

        User user = BmobUser.getCurrentUser(User.class);
        if (user.getStu_num() != null) {
            stu_num.setText(user.getStu_num());
        }
        if (user.getMobilePhoneNumber() != null) {
            phone.setText(user.getMobilePhoneNumber());
        }
        if (user.getEmail() != null) {
            email.setText(user.getEmail());
        }
        if (user.getPortrait_url() != null) {
            Glide.with(this)
                    .load(user.getPortrait_url())
                    .override(500, 500)
                    .centerCrop()
                    .into(portrait);
        } else {
            Glide.with(this)
                    .load(default_portrait_url)
                    .override(500, 500)
                    .centerCrop()
                    .into(portrait);
        }

        final boolean[] is_change_pic = {false};
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(ModifyPersonalInfo.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true, 500, 500, 1, 1);
                is_change_pic[0] = true;
            }
        });


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                User user = BmobUser.getCurrentUser(User.class);
//                user.setStu_num(stu_num.getText().toString());
//                user.setMobilePhoneNumber(phone.getText().toString());
//                user.setEmail(email.getText().toString());

                // 如果图片更改了
//                if (is_change_pic[0]) {
                    // 先上传图片，获取图片地址
                    BmobFile bmobFile = new BmobFile(new File(pictureBean.getPath()));
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                        }
                    });
//                    user.setPortrait_url(bmobFile.getFileUrl());
//                }

//                user.update(user.getObjectId(), new UpdateListener() {
//                    @Override
//                    public void done(BmobException e) {
//                        if (e == null) {
//                            startActivity(new Intent(ModifyPersonalInfo.this, MainActivity.class));
//                        } else {
//                            stu_num.setText("失败" + e.toString());
//                        }
//                    }
//                });

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
                    portrait.setImageBitmap(BitmapFactory.decodeFile(pictureBean.getPath()));
                } else {
                    portrait.setImageURI(pictureBean.getUri());
                }
            }
        }
    }
        private void initUI() {
        stu_num = findViewById(R.id.stu_num);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        submit_btn = findViewById(R.id.submit);
        portrait = findViewById(R.id.portrait);
    }
}
