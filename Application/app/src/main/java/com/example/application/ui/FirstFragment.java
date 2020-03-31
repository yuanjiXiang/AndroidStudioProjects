package com.example.application.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import com.example.application.R;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        默认资料页显示，表白页隐藏，
        1.点击资料按钮，显示资料页，隐藏表白页
        2.点击表白按钮，显示表白页，隐藏资料页
         */
        View data_fragment = Objects.requireNonNull(getActivity()).findViewById(R.id.data_fragment);
        View confession_fragment = getActivity().findViewById(R.id.confession_fragment);

        confession_fragment.setVisibility(View.GONE);

        TabLayout tabLayout = getActivity().findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    data_fragment.setVisibility(View.VISIBLE);
                    confession_fragment.setVisibility(View.GONE);
                } else {
                    confession_fragment.setVisibility(View.VISIBLE);
                    data_fragment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
