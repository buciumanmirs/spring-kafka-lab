package dev.mirotech.kafka.broker.consumer;

import dev.mirotech.kafka.broker.message.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderConsumer {

    @KafkaListener(topics = "t-commodity-order")
    public void consume(OrderMessage message) {
        var totalItemAmount = message.getPrice() * message.getQuantity();
        log.info("Processing order {}, item {}, credit card {}, total amount {}", message.getOrderNumber(),
                message.getItemName(), message.getCreditCardNumber(), totalItemAmount);
    }
}
