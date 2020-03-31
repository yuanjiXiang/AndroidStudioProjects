package com.example.application.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.application.R;
import com.example.application.adapter.TradeAdapter;
import com.example.application.bean.Trade;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TradeFragment extends Fragment {

    private RecyclerView rv;
    private List<Trade> trade;
    private TradeAdapter tradeAdapter;
    private SearchView searchView;

    private void initUI() {
        rv = Objects.requireNonNull(getActivity()).findViewById(R.id.recyclerViewTrade);
        searchView = getActivity().findViewById(R.id.searchView);
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

        initUI();
        final GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 2);
        BmobQuery<Trade> bq = new BmobQuery<>();

        bq.findObjects(new FindListener<Trade>() {
            @Override
            public void done(List<Trade> list, BmobException e) {
                if (e == null) {
                    trade = list;
                    tradeAdapter = new TradeAdapter(getContext(),trade);
                    rv.setLayoutManager(layoutManager2);
                    rv.setAdapter(tradeAdapter);
                } else {
                    Toast.makeText(getActivity(), "获取数据失败" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String bql = "select * from Trade where description = " + "\"" + query + "\""  ;
                bq.setSQL(bql);
                bq.doSQLQuery(new SQLQueryListener<Trade>() {
                    @Override
                    public void done(BmobQueryResult<Trade> bmobQueryResult, BmobException e) {
                        if (e == null) {
                            trade = bmobQueryResult.getResults();
                            tradeAdapter = new TradeAdapter(getContext(),trade);
                            rv.setLayoutManager(layoutManager2);
                            rv.setAdapter(tradeAdapter);
                        } else {
                            Toast.makeText(getActivity(), "获取数据失败" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




    }
}
