package dev.mirotech.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HelloKafkaProducer {

    private final KafkaTemplate<String, String> producer;

    public HelloKafkaProducer(KafkaTemplate<String, String> producer) {
        this.producer = producer;
    }

    public void sendHello(String name) {
        producer.send("t-hello", name);
    }

}
