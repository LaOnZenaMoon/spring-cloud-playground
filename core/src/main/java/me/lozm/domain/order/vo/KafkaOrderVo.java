package me.lozm.domain.order.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.lozm.messageQueue.Payload;
import me.lozm.messageQueue.Schema;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class KafkaOrderVo implements Serializable {

    private Schema schema;
    private Payload payload;

}
