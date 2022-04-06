package com.example.demo.model.DTO;

import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.User;
import lombok.Data;

@Data
public class DetailDTO {

    private User user;
    private Order order;
}
