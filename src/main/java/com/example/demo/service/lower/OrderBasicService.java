package com.example.demo.service.lower;

import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.DTO.ItemChangeDTO;
import com.example.demo.model.DTO.OrderSearchDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.OrderItem;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.OrderInfo;
import com.example.demo.model.Model.resultType.OrderPercentage;
import com.example.demo.utils.LogUtil;
import com.example.demo.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderBasicService {

    @Autowired
    OrderMapper orderMapper;

//    public ResultDTO<List<OrderInfo>> getOrderByUser(User user){
//        List<OrderInfo> orders;
//        if(user.getUserRole() == 0){
//            orders = orderMapper.getOrderByBuyerId(user.getUserId());
//        }else{
//            orders = orderMapper.getOrderByCompanyId(user.getCompanyId());
//        }
//
//        ResultDTO<List<OrderInfo>> resultDTO = new ResultDTO<>();
//        resultDTO.setCode(0);
//        resultDTO.setData(orders);
//        return resultDTO;
//    }
    private ResultDTO<List<String>> getOrderIdList(OrderSearchDTO orderSearchDTO) throws Exception{
        ResultDTO<List<String>> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        List<String> orderIdList = new ArrayList<>();
        orderSearchDTO.setIfSearch(0);
        if((orderSearchDTO.getType() != null && !orderSearchDTO.getType().equals(""))
                || ( orderSearchDTO.getItemName() != null && !orderSearchDTO.getItemName().equals(""))){
            orderIdList = orderMapper.getOrderListByItem(orderSearchDTO);
        }else{
            orderSearchDTO.setIfSearch(1);
        }
        if(orderIdList.size()>0 || orderSearchDTO.getIfSearch()==1){
            if((orderSearchDTO.getOrderId()!=null&& !orderSearchDTO.getOrderId().equals("") )
                    && !orderIdList.contains(orderSearchDTO.getOrderId())){
                orderIdList.add(orderSearchDTO.getOrderId());
            }
        }
//        System.out.println("list");
//        for (String s :
//                orderIdList) {
//            System.out.println(s);
//        }
        resultDTO.setCode(0);
        resultDTO.setData(orderIdList);
        return resultDTO;
    }

    public ResultDTO<List<OrderInfo>> searchOrders(OrderSearchDTO orderSearchDTO) throws Exception{
        ResultDTO<List<OrderInfo>> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        ResultDTO<List<String>> orderIdListResult = getOrderIdList(orderSearchDTO);
        if(orderIdListResult.getCode()<0){
            return resultDTO;
        }
//        if(orderIdListResult.getData().size()==0){
//            resultDTO.setCode(0);
//            resultDTO.setData(Collections.emptyList());
//            return resultDTO;
//        }
        List<String> orderIdList = orderIdListResult.getData();
        orderSearchDTO.setOrderIdList(orderIdList);
        List<OrderInfo> orderInfoResult = Collections.emptyList();
        if(orderIdList.size()!=0 || orderSearchDTO.getIfSearch()==1 ){
            orderInfoResult = orderMapper.getOrderByOrderSearchDTO(orderSearchDTO);
        }
        resultDTO.setData(orderInfoResult);
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<String> uploadOrder(List<List<String>> data) throws Exception{
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        resultDTO.setData("failed");

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(TimeUtil.getTimeStamp());
        orderInfo.setOrderName(data.get(1).get(1));
        orderInfo.setOrderDescription(data.get(3).get(2));
        orderInfo.setOrderStatus(0);
        orderInfo.setBuyerId(data.get(3).get(1));
        orderInfo.setSellerId(data.get(3).get(0));
        orderInfo.setOrderUploadTime(new Date());
        orderInfo.setFirstTime(0);

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (int i = 5; i < data.size(); i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderInfo.getOrderId());
            orderItem.setItemName(data.get(i).get(0));
            orderItem.setItemType(data.get(i).get(1));
            orderItem.setNumber(Integer.parseInt(data.get(i).get(2)));
            orderItem.setCost(Integer.parseInt(data.get(i).get(3)));
            orderItem.setProcess(0);
            orderItems.add(orderItem);
        }
        int orderResult = orderMapper.insertOrder(orderInfo);
        LogUtil.log(getClass().getName(), "order insert finish\n" + orderResult);
        int itemsResult = orderMapper.insertItems(orderItems);
        LogUtil.log(getClass().getName(), "item insert finish\n" + itemsResult);

        resultDTO.setData("success");
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<String> changeOrder(List<List<String>> data) throws Exception{
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        resultDTO.setData("failed");

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(data.get(1).get(0));
        orderInfo.setOrderName(data.get(1).get(1));
        orderInfo.setOrderDescription(data.get(3).get(2));
        orderInfo.setOrderStatus(0);
        orderInfo.setBuyerId(data.get(3).get(1));
        orderInfo.setSellerId(data.get(3).get(0));
        orderInfo.setOrderUploadTime(new Date());
        orderInfo.setFirstTime(0);

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (int i = 5; i < data.size(); i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderInfo.getOrderId());
            orderItem.setItemName(data.get(i).get(0));
            orderItem.setItemType(data.get(i).get(1));
            orderItem.setNumber(Integer.parseInt(data.get(i).get(2)));
            orderItem.setCost(Integer.parseInt(data.get(i).get(3)));
            orderItem.setProcess(0);
            orderItems.add(orderItem);
        }
        int orderResult = orderMapper.insertOrder(orderInfo);
        LogUtil.log(getClass().getName(), "order insert finish\n" + orderResult);
        int deleteItemResult = orderMapper.deleteItems(orderInfo.getOrderId());
        LogUtil.log(getClass().getName(), "item insert finish\n" + deleteItemResult);
        int itemsResult = orderMapper.insertItems(orderItems);
        LogUtil.log(getClass().getName(), "item insert finish\n" + itemsResult);

        resultDTO.setData("success");
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<String> changeProcess(ItemChangeDTO itemChangeDTO) throws Exception{
        ResultDTO<String> resultDTO = new ResultDTO<>();
        int result = orderMapper.changeProcess(itemChangeDTO);
//        System.out.println(result);
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<String> updateFirstTime(String orderId, int value) throws Exception{
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        int result = orderMapper.updateFirstTime(orderId, value);
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<String> updateOrderStatus(String orderId, int value) throws Exception{
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        int result = orderMapper.updateOrderStatus(orderId, value);
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<OrderPercentage> getOrderPercentage(String id, int type) throws Exception{
        ResultDTO<OrderPercentage> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        OrderPercentage orderPercentage = orderMapper.getOrderPercentage(id, type);
        resultDTO.setData(orderPercentage);
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<OrderPercentage> getOrderCost(String id, int type) throws Exception{
        ResultDTO<OrderPercentage> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        OrderPercentage orderCost = orderMapper.getOrderCost(id, type);
        resultDTO.setData(orderCost);
        resultDTO.setCode(0);
        return resultDTO;
    }
}
