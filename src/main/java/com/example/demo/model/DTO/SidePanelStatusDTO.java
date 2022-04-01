package com.example.demo.model.DTO;

import com.example.demo.model.Enum.Menu;
import lombok.Data;

import java.util.List;

@Data
public class SidePanelStatusDTO {
    int curMenu;
    int curSubMenu;
    List<Menu> sidePanel;
    String userId;
}
