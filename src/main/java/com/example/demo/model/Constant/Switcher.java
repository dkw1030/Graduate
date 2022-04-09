package com.example.demo.model.Constant;

import com.example.demo.model.Enum.Menu;

import java.util.Arrays;
import java.util.List;

public class Switcher {
    public static class FilePathSwitcher{
        public static String log_path = "D:\\Project\\Logs\\Java\\Graduate\\log.txt";
        public static String exception_log_path = "D:\\Project\\Logs\\Java\\Graduate\\exception.txt";
        public static String exception_digest_log_path = "D:\\Project\\Logs\\Java\\Graduate\\digestException.txt";

        public static String cloud_file_path_company = "D:\\Project\\FileArea\\Graduate\\Company\\";
        public static String clout_file_path_order = "D:\\Project\\FileArea\\Graduate\\Order\\";
    }

    public static class MenuSwitcher{
        public static int NO_MENU = 0;
        public static int NO_SUBMENU = 0;


        public static int HOME_ID = 11;
        public static int TRADE_BUYER_ID = 1;
        public static int TRADE_SELLER_ID = 2;
        public static int INFO_SELLER_ID = 3;
        public static int PROCESS_ID = 10;
        public static int DISTRIBUTION_ID = 4;
        public static int ANALYSIS_SELLER_ID = 6;
        public static int FRAMEWORK_ID = 7;
        public static int HELP_ID = 8;


        public static int TRADE_BUYER_ORDER_DETAIL_ID = 1;
        public static int TRADE_BUYER_INPUT_ORDER_ID = 2;

        public static int INFO_SELLER_SELLER_DETAIL_ID = 4;
        public static int INFO_SELLER_UPLOAD_SELLER_ID = 5;

        public static int PROCESS_SELLER_INPUT_PROCESS_ID = 6;

        public static int DISTRIBUTION_WAIT_DELIVER_ID = 7;
        public static int DISTRIBUTION_DELIVERING_ID = 8;
        public static int DISTRIBUTION_RESULT_ID = 9;

        public static String HOME_ICON = "home";
        public static String TRADE_ICON = "orders";
        public static String PROCESS_ICON = "process";
        public static String INFO_ICON = "docs";
        public static String DISTRIBUTION_ICON = "distribution";
        public static String ANALYSIS_ICON = "charts";
        public static String FRAMEWORK_ICON = "framework";
        public static String HELP_ICON = "help";

        public static List<Menu> BuyerSidePanel = Arrays.asList(
                Menu.HOME, Menu.INFO_SELLER, Menu.TRADE_BUYER, Menu.PROCESS,
                Menu.DISTRIBUTION, Menu.ANALYSIS_SELLER);

        public static List<Menu> SellerSidePanel = Arrays.asList(
                Menu.HOME, Menu.TRADE_SELLER, Menu.PROCESS,
                Menu.DISTRIBUTION, Menu.ANALYSIS_SELLER, Menu.FRAMEWORK);
    }

    public static class OrderSwitcher{
        public static int ORDER_STATUS_BUYER_SELECT = 0;
        public static int ORDER_STATUS_BUYER_CONFIRM = 1;
        public static int ORDER_STATUS_SELLER_CONFIRM = 2;
        public static int ORDER_STATUS_SELLER_REJECT = 3;
        public static int ORDER_STATUS_SELLER_PREPARING = 4;
        public static int ORDER_STATUS_SELLER_DELIVER = 5;
        public static int ORDER_STATUS_BUYER_RECEIVE = 6;
        public static int ORDER_STATUS_BUYER_REJECT = 7;
    }

    public static class PageSwitcher{
        public static int NUM = 6;
    }
}
