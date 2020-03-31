package com.example.application.bean;

import cn.bmob.v3.BmobObject;

public class Confession extends BmobObject {
    private String content, author;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
