package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeJsonConsumer {


    @Autowired
    private ObjectMapper objectMapper;


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
