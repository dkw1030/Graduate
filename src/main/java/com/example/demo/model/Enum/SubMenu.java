package com.example.demo.model.Enum;

public enum SubMenu {
    Test0(0, "", "test0"),
    Test1(1, "", "test1");
    final int id;
    final String url;
    final String title;

    SubMenu(int id, String url, String title) {
        this.id = id;
        this.url = url;
        this.title = title;
    }
}
