package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Commodity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CommodityNotificationConsumer {

    private final ObjectMapper objectMapper;

    public CommodityNotificationConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "t-commodity", groupId = "commodity-group-notification")
    public void onMessageReceived(String message) {
        try {
            var commodity = objectMapper.readValue(message, Commodity.class);
            log.info("Notification consumer: {}", commodity);
        } catch (Exception e) {
            log.error("Failed to process Commodity message: {}", message, e);
        }
    }


}
