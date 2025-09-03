package dev.mirotech.kafka.broker.producer;

import dev.mirotech.kafka.entiy.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public void sendOrder(Order order) {
        kafkaTemplate.send("t-commodity-order", order.getOrderNumber(), order);
    }
}




