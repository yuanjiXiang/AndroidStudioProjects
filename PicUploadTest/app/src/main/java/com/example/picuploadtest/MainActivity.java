package com.example.picuploadtest;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.graphics.BitmapFactory;

import android.os.Bundle;



import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends AppCompatActivity {

    PictureBean pictureBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        final Button button2 = findViewById(R.id.button2);
        final Button button3 = findViewById(R.id.button3);

        Bmob.initialize(this,"9d17f643cf72354bae0d2fddfd037a2a");

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String picPath = "/storage/emulated/0/Download/timg.jpeg";
                final BmobFile bmobFile = new BmobFile(new File(picPath));
                bmobFile.uploadblock(new UploadFileListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                            Toast.makeText(MainActivity.this,"上传文件成功:" + bmobFile.getFileUrl(),Toast.LENGTH_LONG).show();
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
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(MainActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(false, 200, 200, 1, 1);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = new File("/storage/emulated/0/Download/2.jpeg");
//                button.setText(file.getName());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FTP ftp = new FTP("49.235.250.6","ftpuser","tf7295TFY");
//                            FTP ftp = new FTP("49.235.250.6","root","xm123456.");
                            try {
                                if (ftp.openConnect()) {
                                    File file = new File("/storage/emulated/0/Download/2.jpeg");
                                    File file1 = new File("2.jpeg");
//                                    button.setText(file.getName());
//                                    if (ftp.uploadingSingle(file)) {
////                                        button.setText(file.getName());
//                                        System.out.println("success");
//                                    }
                                }
                                ftp.closeConnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
//                if (!file.exists()) {
//                    Toast.makeText(MainActivity.this,"文件不存在",Toast.LENGTH_LONG).show();
//                    return;
//                }
                }
            });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView imageView = findViewById(R.id.imageView);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                if (pictureBean.isCut()) {
                    imageView.setImageBitmap(BitmapFactory.decodeFile(pictureBean.getPath()));
                } else {
                    imageView.setImageURI(pictureBean.getUri());
                }
            }
        }
    }
}

