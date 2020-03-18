package com.example.application.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.application.R;
import com.example.application.adapter.TradeAdapter;
import com.example.application.bean.Trade;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeFragment extends Fragment {

    private RecyclerView rv;
    List<Trade> trade;
    private TradeAdapter tradeAdapter;
    private SwipeRefreshLayout swipe;

    private void initUI() {
        rv = getActivity().findViewById(R.id.recyclerViewTrade);
        swipe = getActivity().findViewById(R.id.swipe);
    }

    private void refresh() {
        BmobQuery<Trade> bq = new BmobQuery<>();

        final GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 2);

        bq.findObjects(new FindListener<Trade>() {
            @Override
            public void done(List<Trade> list, BmobException e) {
                swipe.setRefreshing(false);
                if (e == null) {
                    trade = list;
                    tradeAdapter = new TradeAdapter(getActivity(), trade);
                    rv.setLayoutManager(layoutManager2);
                    rv.setAdapter(tradeAdapter);
                } else {
                    Toast.makeText(getActivity(), "获取数据失败" + e.toString(), Toast.LENGTH_LONG).show();
                    swipe.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trade, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bmob.initialize(getActivity(), "9d17f643cf72354bae0d2fddfd037a2a");

        initUI();
        refresh();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }
}
