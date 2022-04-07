package com.example.demo.model.DTO;

import lombok.Data;

@Data
public class ItemChangeDTO {
    String orderId;
    String itemName;
    int process;
}
