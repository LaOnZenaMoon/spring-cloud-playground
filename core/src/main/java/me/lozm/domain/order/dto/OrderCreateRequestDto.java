package me.lozm.domain.order.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class OrderCreateRequestDto {

    @NotNull(message = "Product ID cannot be null")
    private String productId;

    @NotNull(message = "Quantity cannot be null")
    @Size(min = 1, message = "Quantity grater than 1")
    private Integer quantity;

}
