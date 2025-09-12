package dev.mirotech.kafka.broker.consumer;

import dev.mirotech.kafka.broker.message.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class OrderConsumer {


    @KafkaListener(topics = "t-commodity-order")
    public void consumeOrder(ConsumerRecord<String, OrderMessage> consumerRecord) {

        var headers = consumerRecord.headers();
        var orderMessage = consumerRecord.value();
        log.info("Kafka Headers: ");

        headers.forEach(header -> log.info("Key: {}, Value: {}", header.key(), new String(header.value())));
        log.info("Order Message: {}", orderMessage);

        var bonusPercentage = Objects.isNull(headers.lastHeader("surpriseBonus")) ? 0 :
                Integer.parseInt(new String(headers.lastHeader("surpriseBonus").value()));
        log.info("Surprise bonus percentage: {}%", bonusPercentage);
    }
}
