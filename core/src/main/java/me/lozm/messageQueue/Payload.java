package me.lozm.messageQueue;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Payload {

    private String order_id;
    private String user_id;
    private String product_id;
    private Integer quantity;
    private Integer unit_price;
    private Integer total_price;

}
