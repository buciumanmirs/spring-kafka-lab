package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

//@Service
@Slf4j
public class EmployeeJsonConsumer {

    final private ObjectMapper objectMapper;

    public EmployeeJsonConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "t-employee-2")
    private void consume(String message) {
        try {
            var value = objectMapper.readValue(message, Employee.class);
            log.info("Received message: {}", value);
        } catch (Exception e) {
            log.error("Error while processing message: {}", message, e);
        }
    }


}
