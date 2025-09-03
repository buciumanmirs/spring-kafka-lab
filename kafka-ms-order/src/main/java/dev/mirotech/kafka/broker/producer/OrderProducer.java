package dev.mirotech.kafka.broker.producer;

import dev.mirotech.kafka.broker.message.OrderMessage;
import dev.mirotech.kafka.entiy.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void sendOrder(OrderMessage orderMessage) {
        kafkaTemplate.send("t-commodity-order", orderMessage.getOrderNumber(), orderMessage)
                .whenComplete((recordMetadata, exception) -> {
                    if (exception == null) {
                        log.info("Order {} sent successfully!", orderMessage.getOrderNumber());
                    } else {
                        log.error("Failed to send order {}", orderMessage.getOrderNumber(), exception);
                    }
                });

        log.info("Just a dummy message for order: {} and item :{}", orderMessage, orderMessage.getItemName());
    }
}




