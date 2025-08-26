package dev.mirotech.kafka;

import dev.mirotech.kafka.producer.CounterProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaCoreProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaCoreProducerApplication.class, args);
    }

    @Autowired
    private CounterProducer counterProducer;

    @Override
    public void run(String... args) throws Exception {
//        counterProducer.sendMessages(100);
    }
}
