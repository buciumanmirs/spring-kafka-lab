package dev.mirotech.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CounterProducer {


    final private KafkaTemplate<String, String> kafkaTemplate;

    public CounterProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    // Create a method that takes an integer parameter number. It then runs for  loop from 0 to the parameter, excluding the parameter itself.
    // Inside the loop, it sends a message to Kafka topic t-counter. The message is a string that starts with "Data " and ends with the current loop index.
    // The message has no key.

    public void sendMessages(int number) {
        for (var i = 0; i < number; i++) {
            var message = "Data " + i;
            kafkaTemplate.send("t-counter", message);
        }

    }


}
