package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.bean.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterPage extends AppCompatActivity {

    private EditText username, password;
    private Button register_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        initUI();
        // 注册
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());

                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(RegisterPage.this,"用户名或者密码为空",Toast.LENGTH_LONG).show();
                } else {
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisterPage.this,"注册成功", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterPage.this, LoginPage.class));
                            } else {
                                Toast.makeText(RegisterPage.this,"注册失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void initUI(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register_btn = findViewById(R.id.register_btn);
    }

}
