package com.example.application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.bean.Data;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PersonalDataApt extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Data> data;
    private String objectId;

    public PersonalDataApt(List<Data> data) {
        this.data = data;
    }
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public EditText content;
        public Button update_btn, delete_btn;
        public RecyclerViewHolder(@NonNull View itemView, int view_type) {
            super(itemView);
            if (view_type == 0) {
                content = itemView.findViewById(R.id.content);
                delete_btn = itemView.findViewById(R.id.delete);
                update_btn = itemView.findViewById(R.id.update);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_data_item_layout,parent,false);
        if (viewType == 0) {
            return new RecyclerViewHolder(view, 0);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final PersonalDataApt.RecyclerViewHolder recyclerViewHolder = (PersonalDataApt.RecyclerViewHolder) holder;
        final Data data1 = data.get(position);
        recyclerViewHolder.content.setText(data1.getContent());

        // 删除资料
        recyclerViewHolder.delete_btn.setOnClickListener(v -> {
            objectId = data1.getObjectId();
            String file_url = data1.getDownload_url();
            Data data2 = new Data();
            data2.setObjectId(objectId);
            // 删除Data表中的数据
            data2.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        // 删除素材表中的数据
                        BmobFile bmobFile = new BmobFile();
                        bmobFile.setUrl(file_url);
                        bmobFile.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    recyclerViewHolder.itemView.setVisibility(View.INVISIBLE);
                                    Toast.makeText(recyclerViewHolder.itemView.getContext(),"删除成功",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });
        });

        // 更新资料描述
        recyclerViewHolder.update_btn.setOnClickListener(v -> {
            objectId = data1.getObjectId();
            Data data2 = new Data();
            data2.setContent(recyclerViewHolder.content.getText().toString());
            data2.update(objectId, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(recyclerViewHolder.itemView.getContext(),"更新成功",Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
