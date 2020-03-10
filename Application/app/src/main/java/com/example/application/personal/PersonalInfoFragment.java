package com.example.application.personal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.application.R;
import com.example.application.adapter.InfoAdapter;
import com.example.application.adapter.MyAdapter;
import com.example.application.bean.Info;
import com.example.application.bean.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoFragment extends Fragment {

    private RecyclerView rv;
    List<Info> data;
    private MyAdapter myAdapter;

    private void initView() {
        rv = getActivity().findViewById(R.id.recyclerview);
    }

    private void Refresh() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Info> Po = new BmobQuery<>();
        if (!user.getUsername().equals("admin")) {
            Po.addWhereEqualTo("author", user.getUsername());
        }
        Po.setLimit(1000);
        Po.findObjects(new FindListener<Info>() {
            @Override
            public void done(List<Info> list, BmobException e) {
                if (e == null) {
                    data = list;
                    myAdapter = new MyAdapter(getActivity(),data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(myAdapter);
                } else {
                    Toast.makeText(getActivity(),"获取数据失败"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bmob.initialize(getActivity(), "9d17f643cf72354bae0d2fddfd037a2a");
        initView();
        Refresh();

    }
}
