package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Commodity;
import dev.mirotech.kafka.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommodityProducer {


    final private KafkaTemplate<String, String> kafkaTemplate;

    final private ObjectMapper objectMapper;

    public CommodityProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Commodity commodity) {
        //convert the employee to Json string and publish it to topic t-employee
        try {
            var employeeJson = objectMapper.writeValueAsString(commodity);
            kafkaTemplate.send("t-commodity", commodity.getName(),  employeeJson);
        } catch (Exception e) {
            log.error("Error while processing message: {}", commodity, e);
        }
    }
}
