package com.example.picuploadtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.BitmapFactory;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    PictureBean pictureBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);

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

                String url = "http://cuitjwxt.cn/";
                File file = new File(pictureBean.getPath());

                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                //创建MultipartBody,给RequestBody进行设置
                MultipartBody multipartBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", "big.jpg", fileBody)
                        .build();
                //创建Request
                Request request = new Request.Builder()
                        .url(url)
                        .post(multipartBody)
                        .build();

                //创建okhttp对象
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .build();

                //上传完图片,得到服务器反馈数据
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("ff", "uploadMultiFile() e=" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("ff", "uploadMultiFile() response=" + response.body().string());
                    }
                });
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

