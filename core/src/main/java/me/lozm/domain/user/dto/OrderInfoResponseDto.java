package me.lozm.domain.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderInfoResponseDto {

    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdDateTime;

    private String orderId;

}
