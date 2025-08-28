package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.SimpleNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleNumberProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendMessage(SimpleNumber simpleNumber) {
        try {
            var json = objectMapper.writeValueAsString(simpleNumber);
            kafkaTemplate.send("t-simple-number", json);
        } catch (JsonProcessingException e) {
            log.error("Error while processing message: {}", simpleNumber, e);
        }
    }
}
