package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class distributionController {
    @RequestMapping("/distribution")
    public String infoSeller(Model model){

        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.INFO_SELLER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.INFO_SELLER_SELLER_DETAIL_ID);

        model.addAttribute("sidePanel", sidePanelStatusDTO);

        return "infoSeller/sellerDetail";
    }
}
