package com.example.picuploadtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.BitmapFactory;

import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.model.object.GetObjectRequest;
import com.tencent.cos.xml.model.object.GetObjectResult;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    PictureBean pictureBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        final Button button2 = findViewById(R.id.button2);


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

                //使用 Glide 加载图片
                /*Glide.with(this)
                        .load(pictureBean.isCut() ? pictureBean.getPath() : pictureBean.getUri())
                        .apply(RequestOptions.centerCropTransform()).into(mIvImage);*/
            }
        }
    }
}

