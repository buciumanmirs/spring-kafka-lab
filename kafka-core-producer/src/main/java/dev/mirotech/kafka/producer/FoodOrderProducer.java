package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.FoodOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodOrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendMessage(FoodOrder order) {
        try {
            var json = objectMapper.writeValueAsString(order);
            kafkaTemplate.send("t-food-order", json);
        }catch (Exception e) {
            log.error("Error while processing message: {}", order, e);
        }
    }
}
