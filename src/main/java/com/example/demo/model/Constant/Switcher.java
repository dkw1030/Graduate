package com.example.demo.model.Constant;

import com.example.demo.model.Enum.Menu;

import java.util.Arrays;
import java.util.List;

public class Switcher {
    public static class FilePathSwitcher{
        public static String log_path = "D:\\Project\\Logs\\Java\\Graduate\\log.txt";
        public static String exception_log_path = "D:\\Project\\Logs\\Java\\Graduate\\exception.txt";
        public static String exception_digest_log_path = "D:\\Project\\Logs\\Java\\Graduate\\digestException.txt";
    }

    public static class MenuSwitcher{
        public static int HOME = 0;
        public static int TRADE_BUYER_ID = 1;
        public static int TRADE_SELLER_ID = 2;
        public static int INFO_SELLER_ID = 3;
        public static int DISTRIBUTION_BUYER_ID = 4;
        public static int DISTRIBUTION_SELLER_ID = 5;
        public static int ANALYSIS_SELLER_ID = 6;
        public static int FRAMEWORK_ID = 7;

        public static List<Menu> BuyerSidePanel = Arrays.asList(Menu.HOME, Menu.TRADE_SELLER);
    }
}
