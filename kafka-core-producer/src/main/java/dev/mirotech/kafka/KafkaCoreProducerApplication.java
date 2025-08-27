package dev.mirotech.kafka;

import dev.mirotech.kafka.entity.PaymentRequest;
import dev.mirotech.kafka.producer.PaymentProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
//@EnableScheduling
public class KafkaCoreProducerApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(KafkaCoreProducerApplication.class, args);
    }

    @Autowired
    private PaymentProducer paymentProducer;

    @Override
    public void run(String... args) throws Exception {
        // Define bank accounts
        var bankAccounts = new String[]{"BANK001", "BANK002", "BANK003"};

        // Create 6 PaymentRequest objects
        var payments = List.of(
                new PaymentRequest(500, "USD", bankAccounts[0], "Payment 1", LocalDate.now()),
                new PaymentRequest(750, "USD", bankAccounts[1], "Payment 2", LocalDate.now()),
                new PaymentRequest(300, "USD", bankAccounts[2], "Payment 3", LocalDate.now()),
                new PaymentRequest(900, "USD", bankAccounts[0], "Payment 4", LocalDate.now()),
                new PaymentRequest(1200, "USD", bankAccounts[1], "Payment 5", LocalDate.now()),
                new PaymentRequest(450, "USD", bankAccounts[2], "Payment 6", LocalDate.now())
        );

        // Publish all PaymentRequests to Kafka
        payments.forEach(paymentProducer::sendMessage);

        // Publish 2 of the PaymentRequests again
        paymentProducer.sendMessage(payments.get(0)); // Publish Payment 1 again
        paymentProducer.sendMessage(payments.get(5)); // Publish Payment 6 again
    }


}
