package dev.mirotech.kafka.broker.consumer;

import dev.mirotech.kafka.broker.message.DiscountMessage;
import dev.mirotech.kafka.broker.message.PromotionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@KafkaListener(topics = "t-commodity-promotion")
public class PromotionConsumer {

    @KafkaHandler
    public void listenPromotion(PromotionMessage message){
        log.info("Received promotion message: {}", message);
    }

    @KafkaHandler
    public void listenDiscount(DiscountMessage message){
        log.info("Received discount message: {}", message);
    }
}
