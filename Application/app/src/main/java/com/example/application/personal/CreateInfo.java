package com.example.application.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.MainActivity;
import com.example.application.R;
import com.example.application.bean.Info;
import com.example.application.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class CreateInfo extends AppCompatActivity {

    private EditText title, content;
    private Button submit_btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_info);

        initUI();
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info info = new Info();
                User user = BmobUser.getCurrentUser(User.class);
                info.setTitle(title.getText().toString());
                info.setContent(content.getText().toString());
                info.setAuthor(user.getUsername());
                info.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            startActivity(new Intent(CreateInfo.this, MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    private void initUI(){
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        submit_btn = findViewById(R.id.submit);
    }
}
