package dev.mirotech.kafka.command.action;

import dev.mirotech.kafka.api.request.DiscountRequest;
import dev.mirotech.kafka.broker.message.DiscountMessage;
import dev.mirotech.kafka.broker.producer.DiscountProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountAction {

    private final DiscountProducer producer;

    public DiscountMessage convertToMessage(DiscountRequest request) {
        return new DiscountMessage(request.getDiscountCode(), request.getDiscountPercentage());
    }

    public void sendToKafka(DiscountMessage message) {
        producer.publish(message);
    }

}
