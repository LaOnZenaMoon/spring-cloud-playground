package me.lozm.api.client;

import me.lozm.domain.order.dto.OrderInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("ORDER-SERVICE-APP")
public interface OrderServiceClient {

    @GetMapping("{userId}/orders")
    List<OrderInfoResponseDto> getOrders(@PathVariable("userId") String userId);

}
