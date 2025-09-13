package dev.mirotech.kafka.broker.producer;

import dev.mirotech.kafka.broker.message.PromotionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionProducer {

    private final KafkaTemplate<String, PromotionMessage> kafkaTemplate;

    public void sendPromotion(PromotionMessage message) {

        try {
            var sendResult = kafkaTemplate.send("t-commodity-promotion", message.getPromotionCode(), message)
                    .get(5, TimeUnit.SECONDS);
            log.info("Promotion code: {} sent successfuly", sendResult.getProducerRecord().value());
        } catch (Exception e) {
            log.error("Error sending promotion: {}", message.getPromotionCode(), e);
        }

        log.info("Just a dummy message for promotion: {}", message);
    }
}
