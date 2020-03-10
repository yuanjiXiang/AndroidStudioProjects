package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class FirstPage extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        Bmob.initialize(this, "9d17f643cf72354bae0d2fddfd037a2a");

        // 延时操作
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            // 如果已登录，跳转到主界面，没登录，跳转到登录界面
            BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
            if (bmobUser != null) {
                startActivity(new Intent(FirstPage.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(FirstPage.this,LoginPage.class));
                finish();
            }
        }
    };
}
