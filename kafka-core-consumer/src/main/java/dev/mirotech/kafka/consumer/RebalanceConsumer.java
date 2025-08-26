package dev.mirotech.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RebalanceConsumer {

    @KafkaListener(topics = {"t-rebalance-alpha", "t-rebalance-beta"}, groupId = "rebalance-group", concurrency = "3")
    public void consumer(String message ) throws InterruptedException {
        TimeUnit.MICROSECONDS.sleep(5);
    }
}
