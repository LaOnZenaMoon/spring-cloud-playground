package me.lozm.api.messageQueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.domain.catalog.entity.Catalog;
import me.lozm.domain.catalog.repository.CatalogRepository;
import me.lozm.domain.catalog.service.CatalogHelperService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CatalogHelperService catalogHelperService;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @KafkaListener(topics = "example-catalog-topic")
    @Transactional
    public void updateQuantity(String kafkaMessage) {
        log.info(String.format("Kafka Message: %s", kafkaMessage));

        Map<Object, Object> map = new HashMap<>();
        try {
            map = objectMapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        String productId = (String) map.get("productId");
        String quantity = (String) map.getOrDefault("quantity", "0");
        Catalog catalog = catalogHelperService.getCatalog(productId);
        catalog.updateStock(Integer.valueOf(quantity));
    }

}
