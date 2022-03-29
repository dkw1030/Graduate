package com.example.demo.utils;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TimeUtil {
    public static String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getTimeStamp(){
        Date date = new Date();
        return date.getTime()+"";
    }

//    public static void main(String[] args) {
//        System.out.println(getTimeStamp());
//    }
}
