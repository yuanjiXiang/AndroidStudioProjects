package com.example.application.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String stu_num;

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }
}
