package com.example.application.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String stu_num;
    private String portrait_url;

    public String getPortrait_url() {
        return portrait_url;
    }

    public void setPortrait_url(String portrait_url) {
        this.portrait_url = portrait_url;
    }

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }
}
