package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Image2Consumer {

    private final ObjectMapper objectMapper;

    @RetryableTopic(attempts = "4", autoCreateTopics = "true",
    topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
    backoff = @Backoff(delay = 3000, maxDelay = 10000, multiplier = 1.5, random = true),
    dltTopicSuffix = "-dead")
    @KafkaListener(
            topics = "t-image-2",
            concurrency = "2"
    )
    public void consumeImage(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws Exception {
        // Convert JSON string to Image object
        var image = objectMapper.readValue(message, Image.class);

        // Throw exception if type is SVG
        if ("svg".equalsIgnoreCase(image.type())) {
            log.warn("SVG images are not allowed: {}", image);
            throw new IllegalArgumentException("SVG images are not allowed: " + image);
        }

        // Log message and partition
        log.info("Received message: {}, Partition: {}", message, partition);
    }


}
