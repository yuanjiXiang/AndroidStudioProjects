package com.example.application.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.LoginPage;
import com.example.application.R;
import com.example.application.bean.User;
import com.example.application.personal.CreateGoods;
import com.example.application.personal.CreateInfo;
import com.example.application.personal.ModifyPersonalInfo;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.http.I;

/**
 * A simple {@link Fragment} subclass.
 * 个人页面
 */
public class MyFragment extends Fragment {

    private TextView username, stu_num, phone, email;
    private Button login_out_btn, myPublish, create_btn, modify_person, create_goods, my_goods;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI();

        // 判断登录状态，获取用户信息
        if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            username.setText(user.getUsername());
            if (user.getStu_num() != "") {
                stu_num.setText(user.getStu_num());
            }
            if (user.getMobilePhoneNumber() != "") {
                phone.setText(user.getMobilePhoneNumber());
            }
            if (user.getEmail() != "") {
                email.setText(user.getEmail());
            }
        } else {
            Toast.makeText(getActivity(), "尚未登录", Toast.LENGTH_LONG).show();
        }

        // 退出登录
        login_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                startActivity(new Intent(getActivity(), LoginPage.class));
            }
        });

        // 进入修改个人信息页面
        modify_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ModifyPersonalInfo.class));
            }
        });

        // 进入个人文章详情页
        myPublish.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_myFragment_to_personalInfoFragment, null));

        // 进入新建文章页面
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateInfo.class));
            }
        });


        // 进入新建商品页面
        create_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateGoods.class));
            }
        });

        // 进入我的商品页
        my_goods.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_myFragment_to_personalGoodsFragment,null));
    }
    private void initUI(){
        username = getActivity().findViewById(R.id.username);
        login_out_btn = getActivity().findViewById(R.id.login_out_btn);
        myPublish = getActivity().findViewById(R.id.myPublish);
        create_btn = getActivity().findViewById(R.id.create);
        stu_num = getActivity().findViewById(R.id.stu_num);
        phone = getActivity().findViewById(R.id.phone);
        email = getActivity().findViewById(R.id.email);
        modify_person = getActivity().findViewById(R.id.modify_person);
        create_goods =getActivity().findViewById(R.id.create_goods);
        my_goods = getActivity().findViewById(R.id.my_goods);
    }
}
