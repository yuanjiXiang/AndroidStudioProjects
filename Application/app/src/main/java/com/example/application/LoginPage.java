package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.bean.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginPage extends AppCompatActivity {

    private EditText username, password;
    private Button login_btn, register_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        initUI();

        // 登录按钮监听
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());

                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginPage.this,"登录成功", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginPage.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginPage.this,"登录失败"+e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        // 注册按钮监听
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
            }
        });

    }

    // 获取界面元素
    private void initUI(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);
    }
}
