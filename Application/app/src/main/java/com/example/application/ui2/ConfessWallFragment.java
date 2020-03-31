package com.example.application.ui2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.application.R;
import com.example.application.adapter.ConfessionApt;
import com.example.application.bean.Confession;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfessWallFragment extends Fragment {

    private ViewPager2 viewPager2;
    private List<Confession> confessionList;
    private ConfessionApt confessionApt;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager2 = Objects.requireNonNull(getActivity()).findViewById(R.id.viewPager2);
        BmobQuery<Confession> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-updatedAt");
        bmobQuery.findObjects(new FindListener<Confession>() {
            @Override
            public void done(List<Confession> list, BmobException e) {
                confessionList = list;
                confessionApt = new ConfessionApt(confessionList);
                viewPager2.setAdapter(confessionApt);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confess_wall, container, false);
    }
}
