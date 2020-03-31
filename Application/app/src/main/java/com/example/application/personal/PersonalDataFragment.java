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
import com.example.application.adapter.PersonalDataApt;
import com.example.application.bean.Data;
import com.example.application.bean.User;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalDataFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Data> data;
    private PersonalDataApt personalDataApt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_data, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.recyclerView);
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Data> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("author", user.getUsername());

        bmobQuery.setLimit(100);
        bmobQuery.findObjects(new FindListener<Data>() {
            @Override
            public void done(List<Data> list, BmobException e) {
                if (e == null) {
                    data = list;
                    personalDataApt = new PersonalDataApt(data);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(personalDataApt);
                } else {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
