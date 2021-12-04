package me.lozm.api.service;

import me.lozm.domain.order.vo.OrderInfoVo;

import java.util.List;

public interface OrderService {

    OrderInfoVo createOrder(OrderInfoVo orderInfoVo);

    OrderInfoVo getOrderDetail(String orderId);

    List<OrderInfoVo> getOrderList(String userId);

}
