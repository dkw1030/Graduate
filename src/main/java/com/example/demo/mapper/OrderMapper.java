package com.example.demo.mapper;

import com.example.demo.model.DTO.ItemChangeDTO;
import com.example.demo.model.DTO.OrderSearchDTO;
import com.example.demo.model.Model.OrderItem;
import com.example.demo.model.Model.resultType.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    int insertOrder(OrderInfo orderInfo);
    int insertItems(List<OrderItem> orderItems);
    int deleteItems(String orderId);
    List<OrderInfo> getOrderByOrderSearchDTO(OrderSearchDTO orderSearchDTO);
    List<String> getOrderListByItem(OrderSearchDTO orderSearchDTO);
    List<OrderItem> getItemsByOrderId(String orderId);
    int changeProcess(ItemChangeDTO item);
    int updateFirstTime(@Param("orderId")String orderId, @Param("value")int value);
    int updateOrderStatus(@Param("orderId")String orderId, @Param("value")int value);
}
