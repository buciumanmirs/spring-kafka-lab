package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseProducer {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(PurchaseRequest request) {
        try {
            var purchaseRequestJson = objectMapper.writeValueAsString(request);
            kafkaTemplate.send("t-purchase-request", request.getRequestNumber(), purchaseRequestJson);
        } catch (JsonProcessingException e) {
            log.error("Error while processing message: {}", request, e);
        }
    }
}
