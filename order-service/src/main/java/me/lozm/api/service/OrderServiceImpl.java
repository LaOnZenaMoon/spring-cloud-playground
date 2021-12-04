package me.lozm.api.service;

import lombok.RequiredArgsConstructor;
import me.lozm.domain.catalog.repository.CatalogRepository;
import me.lozm.domain.catalog.vo.CatalogInfoVo;
import me.lozm.domain.order.entity.Order;
import me.lozm.domain.order.repository.OrderRepository;
import me.lozm.domain.order.vo.OrderInfoVo;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static me.lozm.global.utils.ModelMapperUtils.mapStrictly;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


    @Override
    public OrderInfoVo getOrderDetail(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException(format("주문 정보가 존재하지 않습니다. 주문 ID: %s", orderId)));

        OrderInfoVo orderInfoVo = mapStrictly(order, OrderInfoVo.class);
        return orderInfoVo;
    }

    @Override
    public List<OrderInfoVo> getOrderList(String userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> mapStrictly(order, OrderInfoVo.class))
                .collect(toList());
    }

    @Override
    public OrderInfoVo createOrder(OrderInfoVo orderInfoVo) {
        orderInfoVo.setOrderId(UUID.randomUUID().toString());
        orderInfoVo.setTotalPrice(orderInfoVo.getUnitPrice() * orderInfoVo.getQuantity());

        Order order = mapStrictly(orderInfoVo, Order.class);
        orderRepository.save(order);
        return mapStrictly(order, OrderInfoVo.class);
    }

}
