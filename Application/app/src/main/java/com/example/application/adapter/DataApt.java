package com.example.application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.bean.Data;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class DataApt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Data> dataList;

    public DataApt(List<Data> dataList) {
        this.dataList = dataList;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView content, time;
        Button download;
        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            download = itemView.findViewById(R.id.download);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item_layout,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        final Data data = dataList.get(position);

        recyclerViewHolder.content.setText(data.getContent());
        recyclerViewHolder.time.setText(data.getUpdatedAt());

        recyclerViewHolder.download.setOnClickListener(v -> {
            BmobFile bmobFile = data.getFile();
            if (bmobFile != null) {
                File saveFile = new File("/sdcard/Download/",bmobFile.getFilename());
                bmobFile.download(saveFile, new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Toast.makeText(recyclerViewHolder.itemView.getContext(),"下载成功，路径:" + s,
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
