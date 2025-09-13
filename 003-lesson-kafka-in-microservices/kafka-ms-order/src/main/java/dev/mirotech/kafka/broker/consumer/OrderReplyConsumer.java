package dev.mirotech.kafka.broker.consumer;

import dev.mirotech.kafka.broker.message.OrderReplyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderReplyConsumer {

    @KafkaListener(topics = "t-commodity-order-reply")
    public void consumeOrderReply(OrderReplyMessage message) {
      log.info("Received order reply message: {}", message);
    }
}
