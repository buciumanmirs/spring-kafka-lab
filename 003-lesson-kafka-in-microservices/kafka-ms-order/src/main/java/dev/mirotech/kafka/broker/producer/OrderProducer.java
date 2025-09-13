package dev.mirotech.kafka.broker.producer;

import dev.mirotech.kafka.broker.message.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void sendOrder(OrderMessage orderMessage) {
        var producerRecord = buildProducerRecord(orderMessage);
        kafkaTemplate.send(producerRecord)
                .whenComplete((recordMetadata, exception) -> {
                    if (exception == null) {
                        log.info("Order {} sent successfully!", orderMessage.getOrderNumber());
                    } else {
                        log.error("Failed to send order {}", orderMessage.getOrderNumber(), exception);
                    }
                });

        log.info("Just a dummy message for order: {} and item :{}", orderMessage, orderMessage.getItemName());
    }

    private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage orderMessage) {
        var surpriseBonus = StringUtils.startsWithIgnoreCase(orderMessage.getOrderLocation(), "A") ? 25 : 15;
        var kafkaHeaders = new ArrayList<Header>();
        var surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(surpriseBonus).getBytes());
        kafkaHeaders.add(surpriseBonusHeader);

        return new ProducerRecord<>("t-commodity-order", null, orderMessage.getOrderNumber(), orderMessage, kafkaHeaders);

    }
}




