package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.CarLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarProducer {

    final private KafkaTemplate<String, String> kafkaTemplate;
    final private ObjectMapper objectMapper;

    public void sendMessage(CarLocation carLocation) {
        try {

            var carLocationJson = objectMapper.writeValueAsString(carLocation);
            kafkaTemplate.send("t-location", carLocation.getCarId(), carLocationJson);
        } catch (JsonProcessingException e) {
            log.error("Error while processing message: {}", carLocation, e);
        }
    }


}
