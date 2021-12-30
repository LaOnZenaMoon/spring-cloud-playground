package me.lozm.api.controller;

import lombok.RequiredArgsConstructor;
import me.lozm.api.messageQueue.CatalogProducer;
import me.lozm.api.messageQueue.OrderProducer;
import me.lozm.api.service.OrderService;
import me.lozm.domain.order.dto.OrderCreateRequestDto;
import me.lozm.domain.order.dto.OrderInfoResponseDto;
import me.lozm.domain.order.vo.OrderInfoVo;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static me.lozm.global.utils.ModelMapperUtils.mapStrictly;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final Environment environment;
    private final OrderService orderService;
    private final CatalogProducer catalogProducer;
    private final OrderProducer orderProducer;


    @GetMapping("health-check")
    public String healthCheck() {
        return String.format("Order service is available on PORT %s", environment.getProperty("local.server.port"));
    }

    @GetMapping("orders/{orderId}")
    public ResponseEntity<OrderInfoResponseDto> getOrderDetail(@PathVariable("orderId") String orderId) {
        OrderInfoVo orderInfoVo = orderService.getOrderDetail(orderId);

        OrderInfoResponseDto responseDto = mapStrictly(orderInfoVo, OrderInfoResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping("{userId}/orders")
    public ResponseEntity<List<OrderInfoResponseDto>> getOrderList(@PathVariable("userId") String userId) {
        List<OrderInfoVo> orderList = orderService.getOrderList(userId);

        List<OrderInfoResponseDto> responseList = orderList.stream()
                .map(orderInfoVo -> mapStrictly(orderInfoVo, OrderInfoResponseDto.class))
                .collect(toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }

    @PostMapping("{userId}/orders")
    public ResponseEntity<OrderInfoResponseDto> createOrder(@PathVariable("userId") String userId,
                                                            @RequestBody OrderCreateRequestDto requestDto) {

        OrderInfoVo orderInfoVo = mapStrictly(requestDto, OrderInfoVo.class);
        orderInfoVo.setUserId(userId);
//        OrderInfoVo responseOrderInfoVo = orderService.createOrder(orderInfoVo);

        orderInfoVo.setOrderId(UUID.randomUUID().toString());
        orderInfoVo.setUnitPrice(100);
        orderInfoVo.setTotalPrice(orderInfoVo.getUnitPrice() * orderInfoVo.getQuantity());

        catalogProducer.send("catalogs", orderInfoVo);
        orderProducer.send("orders", orderInfoVo);

//        OrderInfoResponseDto responseDto = mapStrictly(responseOrderInfoVo, OrderInfoResponseDto.class);
        OrderInfoResponseDto responseDto = mapStrictly(orderInfoVo, OrderInfoResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

}
