package com.example.demo.model.Enum;

public enum SubMenu {
    Test0(0, "", "test0"),
    Test1(1, "", "test1");
    public final int id;
    public final String url;
    public final String title;

    SubMenu(int id, String url, String title) {
        this.id = id;
        this.url = url;
        this.title = title;
    }
}
