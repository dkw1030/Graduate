package com.example.demo.mapper;

import com.example.demo.model.DTO.OrderSearchDTO;
import com.example.demo.model.Model.OrderItem;
import com.example.demo.model.Model.resultType.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    int insertOrder(OrderInfo orderInfo);
    int insertItems(List<OrderItem> orderItems);
    List<OrderInfo> getOrderByBuyerId(String userId);
    List<OrderInfo> getOrderByCompanyId(String companyId);
    List<OrderInfo> getOrderByOrderSearchDTO(OrderSearchDTO orderSearchDTO);
    List<String> getOrderListByItem(OrderSearchDTO orderSearchDTO);
}
