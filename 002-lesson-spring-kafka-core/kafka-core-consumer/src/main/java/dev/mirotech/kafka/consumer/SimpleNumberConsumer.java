package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.SimpleNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
@RequiredArgsConstructor
public class SimpleNumberConsumer {

    private final ObjectMapper objectMapper;
    @KafkaListener(topics = "t-simple-number")
    public void consumeSimpleNumber(String message) throws Exception {
        // Convert the JSON string to SimpleNumber object
        var simpleNumber = objectMapper.readValue(message, SimpleNumber.class);

        // Throw IllegalArgumentException if the number is odd
        if (simpleNumber.number() % 2 != 0) {
            throw new IllegalArgumentException("Odd numbers are not allowed: " + simpleNumber.number());
        }

        // Log the number if it's even
        log.info("Received even number: {}", simpleNumber.number());
    }


}
