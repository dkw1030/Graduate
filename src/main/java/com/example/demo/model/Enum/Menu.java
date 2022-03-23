package com.example.demo.model.Enum;

import com.example.demo.model.Constant.Switcher.MenuSwitcher;
import com.example.demo.model.Enum.SubMenu;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Menu {
    HOME(MenuSwitcher.HOME, "", "主页", Collections.emptyList()),

    TRADE_BUYER(MenuSwitcher.TRADE_BUYER_ID, "", "采购协同", Arrays.asList(SubMenu.Test0, SubMenu.Test1)),
    TRADE_SELLER(MenuSwitcher.TRADE_SELLER_ID, "", "销售协同", Collections.emptyList()),

    INFO_SELLER(MenuSwitcher.INFO_SELLER_ID, "", "供应商档案", Collections.emptyList()),

    DISTRIBUTION_BUYER(MenuSwitcher.DISTRIBUTION_BUYER_ID, "", "物流", Collections.emptyList()),
    DISTRIBUTION_SELLER(MenuSwitcher.DISTRIBUTION_SELLER_ID, "", "物流", Collections.emptyList()),

    ANALYSIS_SELLER(MenuSwitcher.ANALYSIS_SELLER_ID, "", "订单分析", Collections.emptyList()),

    FRAMEWORK(MenuSwitcher.FRAMEWORK_ID, "", "架构管理", Collections.emptyList());

    final int id;
    final String url;
    final String title;
    final List<SubMenu> subMenus;

    Menu(int id, String url, String title, List<SubMenu> subMenus){
        this.id = id;
        this.url = url;
        this.title = title;
        this.subMenus = subMenus;
    }
}
