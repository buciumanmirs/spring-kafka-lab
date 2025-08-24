package dev.mirotech.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
public class HelloKafkaAIProducer {

    private final KafkaTemplate<String, String> producer;

    public HelloKafkaAIProducer(KafkaTemplate<String, String> producer) {
        this.producer = producer;
    }

    public void sendHello(String name) {
        producer.send("t-hello", name);
    }

    public void sendHelloMessage(String name) {
        String message = "Hello, " + name;
        producer.send("t-hello", message);
    }

}
