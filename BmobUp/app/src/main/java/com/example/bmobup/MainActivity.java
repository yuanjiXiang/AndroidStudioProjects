package com.example.bmobup;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends AppCompatActivity {
    String picUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this,"9d17f643cf72354bae0d2fddfd037a2a");

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }

        final ImageView imageView = findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                upload();
                Glide.with(MainActivity.this)
                        .load("http://bmob-cdn-27983.bmobpay.com/2020/03/12/4368a42aae324a078f59c253458294c6.jpeg")
                        .into(imageView);
            }
        });

    }

    private void upload(){
        String picPath = "/storage/emulated/0/Download/timg.jpeg";
        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Toast.makeText(MainActivity.this,"上传文件成功:" + bmobFile.getFileUrl(),Toast.LENGTH_LONG).show();
                    picUrl = bmobFile.getFileUrl();
                }else{
                    Toast.makeText(MainActivity.this,"上传文件失败:" +  e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }
}
