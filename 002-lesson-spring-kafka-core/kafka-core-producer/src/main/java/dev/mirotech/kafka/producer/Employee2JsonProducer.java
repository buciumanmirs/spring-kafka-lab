package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

//@Service
@Slf4j
public class Employee2JsonProducer {


    final private KafkaTemplate<String, String> kafkaTemplate;

    final private ObjectMapper objectMapper;

    public Employee2JsonProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Employee employee) {
        //convert the employee to Json string and publish it to topic t-employee
        try {
            var employeeJson = objectMapper.writeValueAsString(employee);
            kafkaTemplate.send("t-employee-2", employeeJson);
        } catch (Exception e) {
            log.error("Error while processing message: {}", employee, e);

        }
    }
}
