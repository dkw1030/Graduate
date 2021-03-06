package com.example.demo.model.Enum;

import com.example.demo.model.Constant.Switcher;

public enum SubMenu {
    TRADE_BUYER_ORDER_DETAIL(Switcher.MenuSwitcher.TRADE_BUYER_ORDER_DETAIL_ID, "/orderList", "订单详情"),
    TRADE_BUYER_INPUT_ORDER(Switcher.MenuSwitcher.TRADE_BUYER_INPUT_ORDER_ID, "/uploadOrder", "导入订单"),
    //TRADE_BUYER_OUTPUT_ORDER(Switcher.MenuSwitcher.TRADE_BUYER_OUTPUT_ORDER_ID, "", "导出订单"),

    INFO_SELLER_SELLER_DETAIL(Switcher.MenuSwitcher.INFO_SELLER_SELLER_DETAIL_ID, "/sellerList", "供应商详情"),
    INFO_SELLER_UPLOAD_SELLER(Switcher.MenuSwitcher.INFO_SELLER_UPLOAD_SELLER_ID, "/uploadSeller", "导入供应商信息"),

    PROCESS_SELLER_INPUT_PROCESS(Switcher.MenuSwitcher.PROCESS_SELLER_INPUT_PROCESS_ID, "/process/process", "导入进度"),

    DISTRIBUTION_WAIT_DELIVER(Switcher.MenuSwitcher.DISTRIBUTION_WAIT_DELIVER_ID, "/distribution/waitDeliverOrderList", "待发货"),
    DISTRIBUTION_DELIVERING(Switcher.MenuSwitcher.DISTRIBUTION_DELIVERING_ID, "/distribution/deliveringOrderList", "待收货"),
    DISTRIBUTION_RESULT(Switcher.MenuSwitcher.DISTRIBUTION_RESULT_ID, "/distribution/waitReceiveOrderList", "已签收");

    public final int id;
    public final String url;
    public final String title;

    SubMenu(int id, String url, String title) {
        this.id = id;
        this.url = url;
        this.title = title;
    }
}
