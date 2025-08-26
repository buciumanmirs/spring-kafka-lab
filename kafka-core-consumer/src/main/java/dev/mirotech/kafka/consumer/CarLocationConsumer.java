package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.CarLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarLocationConsumer {


    private final ObjectMapper objectMapper;

    public CarLocationConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "t-location", groupId = "group-location-all")
    public void listenAll(String message) throws JsonProcessingException {
        var carLocation = objectMapper.readValue(message, CarLocation.class);
        log.info("listenAll location: {}", carLocation);
    }


    @KafkaListener(topics = "t-location", groupId = "group-location-far")
    public void listenFar(String message) throws JsonProcessingException {
        var carLocation = objectMapper.readValue(message, CarLocation.class);
        if (carLocation.getDistance() < 100) {
            return;
        }
        log.info("listenFar location: {}", carLocation);
    }
}
