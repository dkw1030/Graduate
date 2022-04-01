package com.example.demo.model.DTO;

import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.User;
import lombok.Data;

import java.util.List;
@Data
public class TradeDTO {
    User user;
    List<Order> orders;
}
