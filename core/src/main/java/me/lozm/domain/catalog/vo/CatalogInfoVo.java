package me.lozm.domain.catalog.vo;

import lombok.Getter;

@Getter
public class CatalogInfoVo {

    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

}
