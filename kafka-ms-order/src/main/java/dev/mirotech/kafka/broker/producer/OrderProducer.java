package dev.mirotech.kafka.broker.producer;

import dev.mirotech.kafka.entiy.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public void sendOrder(Order order) {
        kafkaTemplate.send("t-commodity-order", order.getOrderNumber(), order)
                .whenComplete((recordMetadata, exception) -> {
                    if (exception == null) {
                        log.info("Order {} sent successfully!", order.getOrderNumber());
                    } else {
                        log.error("Failed to send order {}", order.getOrderNumber(), exception);
                    }
                });

        log.info("Just a dummy message for order: {} and item :{}", order, order.getOrderItems().getFirst().getItemName());
    }
}




