package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
@RequiredArgsConstructor
public class ImageConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "t-image",
            concurrency = "2",
            containerFactory = "imageRetryContainerFactory"
    )
    public void consumeImage(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws Exception {
        // Convert JSON string to Image object
        var image = objectMapper.readValue(message, Image.class);

        // Throw exception if type is SVG
        if ("svg".equalsIgnoreCase(image.type())) {
            throw new IllegalArgumentException("SVG images are not allowed: " + image);
        }

        // Log message and partition
        log.info("Received message: {}, Partition: {}", message, partition);
    }


}
