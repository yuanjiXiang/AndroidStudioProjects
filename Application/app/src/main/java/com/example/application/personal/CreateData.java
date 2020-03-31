package com.example.application.personal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.MainActivity;
import com.example.application.R;
import com.example.application.bean.Data;
import com.example.application.bean.User;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class CreateData extends AppCompatActivity {
    private Button select_file, submit;
    private TextView file_path;
    private EditText content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

        initUI();
        // 选择文件
        select_file.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);
        });

        // 上传文件
        submit.setOnClickListener(v -> {
            BmobFile bmobFile = new BmobFile(new File(file_path.getText().toString().substring(15)));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Data data = new Data();
                        User user = BmobUser.getCurrentUser(User.class);
                        data.setContent(content.getText().toString());
                        data.setFile(bmobFile);
                        data.setDownload_url(bmobFile.getFileUrl());
                        data.setAuthor(user.getUsername());
                        data.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "上传文件成功:", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(CreateData.this, MainActivity.class));
                                }
                            }
                        });
                    }
                }
            });
        });
    }

    // 文件选择结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            assert data != null;
            Uri uri = data.getData();
            assert uri != null;
            file_path.setText(uri.getPath());
        }
    }

    private void initUI() {
        select_file = findViewById(R.id.select_file);
        submit = findViewById(R.id.update);
        file_path = findViewById(R.id.file_path);
        content = findViewById(R.id.content);
    }
}

