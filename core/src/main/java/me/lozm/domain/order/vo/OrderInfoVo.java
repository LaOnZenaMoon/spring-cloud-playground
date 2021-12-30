package me.lozm.domain.order.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
public class OrderInfoVo {

    @Setter
    private String orderId;
    private String productId;
    private Integer quantity;
    @Setter
    private Integer unitPrice;
    @Setter
    private Integer totalPrice;
    @Setter
    private String userId;

}
