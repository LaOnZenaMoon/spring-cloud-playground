package me.lozm.api.messageQueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.domain.order.vo.OrderInfoVo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public OrderInfoVo send(String topic, OrderInfoVo orderInfoVo) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(orderInfoVo);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        kafkaTemplate.send(topic, jsonString);
        log.info(String.format("Kafka Producer sent data from Order microservice: %s", orderInfoVo));

        return orderInfoVo;
    }

}
