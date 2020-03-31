package com.example.application.personal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.application.R;
import com.example.application.adapter.PersonalGoodsApt;
import com.example.application.bean.Trade;
import com.example.application.bean.User;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalGoodsFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Trade> data;
    private PersonalGoodsApt personalGoodsApt;

    private void initUI() {
        recyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.recyclerView);
    }

    private void refresh() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Trade> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("owner", user.getUsername());

        bmobQuery.setLimit(20);
        bmobQuery.findObjects(new FindListener<Trade>() {
            @Override
            public void done(List<Trade> list, BmobException e) {
                if (e == null) {
                    data = list;
                    personalGoodsApt = new PersonalGoodsApt(data, getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(personalGoodsApt);
                } else {
                    Toast.makeText(getActivity(),"获取失败"+e.getErrorCode(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_goods, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bmob.initialize(getActivity(), "9d17f643cf72354bae0d2fddfd037a2a");
        initUI();
        refresh();
    }
}
