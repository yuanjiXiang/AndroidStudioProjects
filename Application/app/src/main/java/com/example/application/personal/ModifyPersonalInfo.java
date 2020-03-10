package com.example.application.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.MainActivity;
import com.example.application.R;
import com.example.application.bean.User;
import com.example.application.ui.MyFragment;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyPersonalInfo extends AppCompatActivity {

    private EditText stu_num, phone, email;
    private Button submit_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_personal);

        initUI();
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = BmobUser.getCurrentUser(User.class);
                user.setStu_num(stu_num.getText().toString());
                user.setMobilePhoneNumber(phone.getText().toString());
                user.setEmail(email.getText().toString());
                user.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            startActivity(new Intent(ModifyPersonalInfo.this, MainActivity.class));
                        } else {
                            stu_num.setText("失败" + e.toString());
                        }
                    }
                });

            }
        });
    }

    private void initUI() {
        stu_num = findViewById(R.id.stu_num);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        submit_btn = findViewById(R.id.submit);
    }
}
