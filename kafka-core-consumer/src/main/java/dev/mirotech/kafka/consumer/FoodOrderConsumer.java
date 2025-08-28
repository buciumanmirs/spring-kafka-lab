package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.FoodOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodOrderConsumer {

    private final ObjectMapper objectMapper;

    private static final int MAX_AMOUNT_ORDER = 7;

    @KafkaListener(topics = "t-food-order", errorHandler = "foodOrderErrorHandler")
    public void consumeFoodOrder(String message) throws Exception {

        // Deserialize the JSON string into a FoodOrder object
        var foodOrder = objectMapper.readValue(message, FoodOrder.class);

        // Check if the amount exceeds the maximum allowed amount
        if (foodOrder.amount() > MAX_AMOUNT_ORDER) {
            log.error("Order amount exceeds maximum limit: {}", foodOrder);
            throw new IllegalArgumentException("Order amount exceeds the maximum allowed limit: " + MAX_AMOUNT_ORDER);
        }

        // Log the food order if it passes the validation
        log.info("Food order processed: {}", foodOrder);

    }


}
