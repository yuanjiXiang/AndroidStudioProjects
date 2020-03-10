package com.example.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.bean.Info;
import com.example.application.bean.User;

import java.util.List;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Info> data;
    private Context context;
    private String objectId;
    public MyAdapter(Context context, List<Info> data) {
        this.data = data;
        this.context = context;
    }
    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public EditText title, content;
        public Button update_btn, delete_btn;

        public RecyclerViewHolder(@NonNull View itemView, int view_type) {
            super(itemView);
            if (view_type == 0) {
                title = itemView.findViewById(R.id.title);
                content = itemView.findViewById(R.id.content);
                delete_btn = itemView.findViewById(R.id.delete);
                update_btn = itemView.findViewById(R.id.update);
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_item_layout,parent,false);
        View loading_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_layout,parent,false);
        if (viewType  == 1) {
            return new RecyclerViewHolder(loading_view, 1); // 返回正在加载中
        } else {
            return new RecyclerViewHolder(view, 0); // 返回加载完成的item
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        final Info info = data.get(position);

        recyclerViewHolder.title.setText(info.getTitle());
        recyclerViewHolder.content.setText(info.getContent());

        // 删除文章
        recyclerViewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectId = info.getObjectId();
                Info info2 = new Info();
                info2.setObjectId(objectId);
                info2.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            recyclerViewHolder.itemView.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        // 更新文章
        recyclerViewHolder.update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectId = info.getObjectId();
                Info info1 = new Info();
                info1.setTitle(recyclerViewHolder.title.getText().toString());
                info1.setContent(recyclerViewHolder.content.getText().toString());
                info1.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
