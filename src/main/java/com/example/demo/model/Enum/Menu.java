package com.example.demo.model.Enum;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.Constant.Switcher.MenuSwitcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Menu {
    HOME(MenuSwitcher.HOME_ID, "/test7/", "主页", MenuSwitcher.HOME_ICON, Collections.emptyList()),

    TRADE_BUYER(MenuSwitcher.TRADE_BUYER_ID, "#", "采购协同", MenuSwitcher.TRADE_ICON, Arrays.asList(
            SubMenu.TRADE_BUYER_ORDER_DETAIL,
            SubMenu.TRADE_BUYER_INPUT_ORDER)),
    TRADE_SELLER(MenuSwitcher.TRADE_SELLER_ID, "/test7/", "销售协同", MenuSwitcher.TRADE_ICON, Collections.emptyList()),

    PROCESS(MenuSwitcher.PROCESS_SELLER_ID, "", "订单进度", MenuSwitcher.PROCESS_ICON, Collections.emptyList()),

    INFO_SELLER(MenuSwitcher.INFO_SELLER_ID, "#", "供应商档案", MenuSwitcher.INFO_ICON, Arrays.asList(
            SubMenu.INFO_SELLER_INFO_DETAIL,
            SubMenu.INFO_SELLER_INPUT_INFO)),

    DISTRIBUTION(MenuSwitcher.DISTRIBUTION_ID, "#", "物流", MenuSwitcher.DISTRIBUTION_ICON, Arrays.asList(
            SubMenu.DISTRIBUTION_WAIT_DELIVER,
            SubMenu.DISTRIBUTION_DELIVERING,
            SubMenu.DISTRIBUTION_RESULT)),

    ANALYSIS_SELLER(MenuSwitcher.ANALYSIS_SELLER_ID, "", "订单分析", MenuSwitcher.ANALYSIS_ICON, Collections.emptyList()),

    HELP(MenuSwitcher.HELP_ID, "", "帮助", MenuSwitcher.HELP_ICON, Collections.emptyList()),

    FRAMEWORK(MenuSwitcher.FRAMEWORK_ID, "", "架构管理", MenuSwitcher.FRAMEWORK_ICON, Collections.emptyList());

    public final int id;
    public final String url;
    public final String title;
    public final String icon;
    public final List<SubMenu> subMenus;

    Menu(int id, String url, String title, String icon, List<SubMenu> subMenus){
        this.id = id;
        this.url = url;
        this.title = title;
        this.icon = icon;
        this.subMenus = subMenus;
    }
}
