package dev.mirotech.kafka.command.action;

import dev.mirotech.kafka.api.request.PromotionRequest;
import dev.mirotech.kafka.broker.message.PromotionMessage;
import dev.mirotech.kafka.broker.producer.PromotionProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionAction {

    private final PromotionProducer producer;

    public static PromotionMessage toPromotionMessage(PromotionRequest request) {
        if (request == null) {
            return null;
        }
        return new PromotionMessage(request.getPromotionCode());
    }

    public void sendToKafka(PromotionMessage message) {
        producer.sendPromotion(message);
    }

}
