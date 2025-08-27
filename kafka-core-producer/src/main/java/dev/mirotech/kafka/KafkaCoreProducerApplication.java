package dev.mirotech.kafka;

import dev.mirotech.kafka.entity.PurchaseRequest;
import dev.mirotech.kafka.producer.CounterProducer;
import dev.mirotech.kafka.producer.PurchaseProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.UUID;

@SpringBootApplication
//@EnableScheduling
public class KafkaCoreProducerApplication implements CommandLineRunner {

    public KafkaCoreProducerApplication(PurchaseProducer purchaseProducer) {
        this.purchaseProducer = purchaseProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaCoreProducerApplication.class, args);
    }

    private final PurchaseProducer purchaseProducer;


    @Override
    public void run(String... args) throws Exception {
        // Create and send three unique PurchaseRequest objects
        PurchaseRequest request1 = null;
        for (var i = 1; i <= 3; i++) {
            var request = new PurchaseRequest(
                    UUID.randomUUID(),               // Unique ID
                    "REQ-" + i,                      // Unique request number
                    i * 100,                         // Custom amount for each request
                    "USD"                            // Currency
            );
            if (i == 1) {request1 = request;}
            purchaseProducer.sendMessage(request);
        }
        purchaseProducer.sendMessage(request1);
    }

}
