package com.example.application.ui2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.application.R;
import com.example.application.adapter.DataApt;
import com.example.application.bean.Data;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataShareFragment extends Fragment {

    private RecyclerView recyclerView;
    List<Data> dataList;
    private DataApt dataApt;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recyclerView);

        BmobQuery<Data> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-updatedAt");
        bmobQuery.findObjects(new FindListener<Data>() {
            @Override
            public void done(List<Data> list, BmobException e) {
                if (e == null) {
                    dataList = list;
                    dataApt = new DataApt(dataList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(dataApt);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_share, container, false);
    }
}
