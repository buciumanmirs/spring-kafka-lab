package dev.mirotech.kafka;

import dev.mirotech.kafka.entity.FoodOrder;
import dev.mirotech.kafka.entity.SimpleNumber;
import dev.mirotech.kafka.producer.FoodOrderProducer;
import dev.mirotech.kafka.producer.SimpleNumberProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableScheduling
public class KafkaCoreProducerApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(KafkaCoreProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }


}
