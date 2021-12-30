package me.lozm.api.messageQueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.domain.order.vo.KafkaOrderVo;
import me.lozm.domain.order.vo.OrderInfoVo;
import me.lozm.messageQueue.Field;
import me.lozm.messageQueue.Payload;
import me.lozm.messageQueue.Schema;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<Field> fields = Arrays.asList(
            new Field("string", true, "order_id"),
            new Field("string", true, "user_id"),
            new Field("string", true, "product_id"),
            new Field("int32", true, "quantity"),
            new Field("int32", true, "unit_price"),
            new Field("int32", true, "total_price")
    );
    private final Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    public OrderInfoVo send(String topic, OrderInfoVo orderInfoVo) {
        Payload payload = Payload.builder()
                .order_id(orderInfoVo.getOrderId())
                .user_id(orderInfoVo.getUserId())
                .product_id(orderInfoVo.getProductId())
                .quantity(orderInfoVo.getQuantity())
                .unit_price(orderInfoVo.getUnitPrice())
                .total_price(orderInfoVo.getTotalPrice())
                .build();

        KafkaOrderVo kafkaOrderVo = new KafkaOrderVo(schema, payload);

        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(kafkaOrderVo);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        kafkaTemplate.send(topic, jsonString);
        log.info(String.format("Order Producer sent data from Order microservice: %s", kafkaOrderVo));

        return orderInfoVo;
    }

}
