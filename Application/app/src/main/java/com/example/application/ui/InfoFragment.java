package com.example.application.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.application.R;
import com.example.application.adapter.InfoAdapter;
import com.example.application.bean.Info;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    private RecyclerView rv;
    private List<Info> infoList;
    private InfoAdapter infoAdapter;

    private void initView() {
        rv = Objects.requireNonNull(getActivity()).findViewById(R.id.recyclerViewInfo);
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

        initView();
        BmobQuery<Info> Po = new BmobQuery<>();
        Po.order("-updatedAt");
//        final GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(),1);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager( 1,StaggeredGridLayoutManager.VERTICAL );
        Po.findObjects(new FindListener<Info>() {
            @Override
            public void done(List<Info> list, BmobException e) {
                if (e == null) {
                    infoList = list;
                    infoAdapter = new InfoAdapter(infoList);
                    rv.setAdapter(infoAdapter);
                    rv.setLayoutManager(layoutManager);
                } else {
                    Toast.makeText(getActivity(),"获取数据失败"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
