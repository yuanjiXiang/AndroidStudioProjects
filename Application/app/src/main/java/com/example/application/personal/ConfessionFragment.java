package com.example.application.personal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.application.R;
import com.example.application.bean.Confession;
import com.example.application.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 个人表白管理页.
 */
public class ConfessionFragment extends Fragment {

    private Button submit, update, delete;
    private EditText content;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        submit = getActivity().findViewById(R.id.submit);
        update = getActivity().findViewById(R.id.update);
        delete = getActivity().findViewById(R.id.delete);
        content = getActivity().findViewById(R.id.content);

        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Confession> confessionBmobQuery = new BmobQuery<>();
        confessionBmobQuery.addWhereEqualTo("author", user.getUsername());
        confessionBmobQuery.findObjects(new FindListener<Confession>() {
            @Override
            public void done(List<Confession> list, BmobException e) {
                if (e == null) {
                    // 如果有查询到有表白记录，将表白记录显示在可编辑区上，以二次修改，并隐藏提交键
                    content.setText(list.get(0).getContent());
                    submit.setVisibility(View.INVISIBLE);

                    // 更新表白
                    update.setOnClickListener(v -> {
                        Confession confession = new Confession();
                        confession.setContent(content.getText().toString());
                        confession.update(list.get(0).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();
                            }
                        });
                    });

                    // 删除表白
                    delete.setOnClickListener(v -> {
                        Confession confession = new Confession();
                        confession.setObjectId(list.get(0).getObjectId());
                        confession.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    content.setText("");
                                    Toast.makeText(getContext(),"删除" +
                                            "成功",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    });
                } else {
                    // 如果没有查询到表白记录，隐藏更新键， 删除键
                    update.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.INVISIBLE);
                    submitConfession(content);
                }
            }
        });

    }


    // 提交新表白
    private void submitConfession(EditText content) {
        submit.setOnClickListener(v -> {
            Confession confession= new Confession();
            User user = BmobUser.getCurrentUser(User.class);
            confession.setContent(content.getText().toString());
            confession.setAuthor(user.getUsername());
            confession.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Toast.makeText(getContext(),"上传成功",Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confession, container, false);
    }
}
