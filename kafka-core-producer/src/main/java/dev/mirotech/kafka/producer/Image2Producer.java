package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Image2Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public Image2Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendImageToPartition(Image image, int partition) {

        try {
            var imageJson = objectMapper.writeValueAsString(image);
            kafkaTemplate.send("t-image-2", partition, image.type(), imageJson);
        } catch (Exception e) {
            log.error("Error while processing message: {}", image, e);
        }

    }
}
