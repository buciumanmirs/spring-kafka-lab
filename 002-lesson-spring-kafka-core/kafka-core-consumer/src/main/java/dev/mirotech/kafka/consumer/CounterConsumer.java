package dev.mirotech.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CounterConsumer {

    @KafkaListener(topics = "t-counter", groupId = "counter-group-fast")
    public void consumeFast(String message) {
        log.info("Fast consumer: {}", message);
    }

    @KafkaListener(topics = "t-counter", groupId = "counter-group-slow")
    public void consumeSlow(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        log.info("Slow consumer: {}", message);
    }

}
