package com.example.application.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.application.R;
import com.example.application.adapter.InfoAdapter;
import com.example.application.bean.Info;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    private RecyclerView rv;
    private SwipeRefreshLayout swipe;
    List<Info> data;
    private InfoAdapter infoAdapter;

    private void initView() {
        rv = getActivity().findViewById(R.id.recyclerView);
        swipe = getActivity().findViewById(R.id.swipe);
    }

    private void Refresh() {
        BmobQuery<Info> Po = new BmobQuery<>();
        Po.order("-updatedAt");
        Po.setLimit(1000);
        Po.findObjects(new FindListener<Info>() {
            @Override
            public void done(List<Info> list, BmobException e) {
                if (e == null) {
                    data = list;
                    infoAdapter = new InfoAdapter(getActivity(),data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(infoAdapter);
                } else {
                    swipe.setRefreshing(false);
                    Toast.makeText(getActivity(),"获取数据失败"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bmob.initialize(getActivity(), "9d17f643cf72354bae0d2fddfd037a2a");
        initView();
        Refresh();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
                swipe.setRefreshing(false);
            }
        });
    }
}
