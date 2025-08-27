package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(PaymentRequest request) {
        try {
            var paymentRequestJson = objectMapper.writeValueAsString(request);
            kafkaTemplate.send("t-payment-request",  paymentRequestJson);
        } catch (JsonProcessingException e) {
            log.error("Error while processing message: {}", request, e);
        }
    }
}
