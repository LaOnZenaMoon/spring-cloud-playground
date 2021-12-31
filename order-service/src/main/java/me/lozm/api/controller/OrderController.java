package me.lozm.api.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final Environment environment;
    private final OrderService orderService;
    private final CatalogProducer catalogProducer;
    private final OrderProducer orderProducer;


    @GetMapping("health-check")
    @Timed(value = "orders.health-check", longTask = true)
    public String healthCheck() {
        return String.format("Order service is available on PORT %s", environment.getProperty("local.server.port"));
    }

    @GetMapping("orders/{orderId}")
    @Timed(value = "orders.detail", longTask = true)
    public ResponseEntity<OrderInfoResponseDto> getOrderDetail(@PathVariable("orderId") String orderId) {
        OrderInfoVo orderInfoVo = orderService.getOrderDetail(orderId);

        OrderInfoResponseDto responseDto = mapStrictly(orderInfoVo, OrderInfoResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping("{userId}/orders")
    @Timed(value = "orders.list", longTask = true)
    public ResponseEntity<List<OrderInfoResponseDto>> getOrderList(@PathVariable("userId") String userId) {
        log.info("Before receive orders data");
        List<OrderInfoVo> orderList = orderService.getOrderList(userId);

        List<OrderInfoResponseDto> responseList = orderList.stream()
                .map(orderInfoVo -> mapStrictly(orderInfoVo, OrderInfoResponseDto.class))
                .collect(toList());
        log.info("After receive orders data");

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }

    @PostMapping("{userId}/orders")
    @Timed(value = "orders.create", longTask = true)
    public ResponseEntity<OrderInfoResponseDto> createOrder(@PathVariable("userId") String userId,
                                                            @RequestBody OrderCreateRequestDto requestDto) {

        log.info("Before add orders data");

        OrderInfoVo orderInfoVo = mapStrictly(requestDto, OrderInfoVo.class);
        orderInfoVo.setUserId(userId);
        OrderInfoVo responseOrderInfoVo = orderService.createOrder(orderInfoVo);
        OrderInfoResponseDto responseDto = mapStrictly(responseOrderInfoVo, OrderInfoResponseDto.class);

//        orderInfoVo.setOrderId(UUID.randomUUID().toString());
//        orderInfoVo.setUnitPrice(100);
//        orderInfoVo.setTotalPrice(orderInfoVo.getUnitPrice() * orderInfoVo.getQuantity());
//
//        catalogProducer.send("catalogs", orderInfoVo);
//        orderProducer.send("orders", orderInfoVo);
//        OrderInfoResponseDto responseDto = mapStrictly(orderInfoVo, OrderInfoResponseDto.class);

        log.info("After add orders data");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

}
