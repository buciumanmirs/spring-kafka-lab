package dev.mirotech.kafka.broker.consumer;

import dev.mirotech.kafka.broker.message.PromotionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PromotionUppercaseListener {

    @KafkaListener(topics = "t-commodity-promotion-uppercase")
    public void listPromotion(PromotionMessage message) {
        log.info("Processing uppercase message: {}", message);

    }
}
