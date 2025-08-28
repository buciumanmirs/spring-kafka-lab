package dev.mirotech.kafka;

import dev.mirotech.kafka.entity.FoodOrder;
import dev.mirotech.kafka.producer.FoodOrderProducer;
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

    @Autowired
    private FoodOrderProducer foodOrderProducer;

    @Override
    public void run(String... args) throws Exception {
        var chickenOrder = new FoodOrder("Chicken", 3);
        var pizzaOrder = new FoodOrder("Pizza", 5);
        var pastaOrder = new FoodOrder("Pasta", 10);

        foodOrderProducer.sendMessage(chickenOrder);
        foodOrderProducer.sendMessage(pizzaOrder);
        foodOrderProducer.sendMessage(pastaOrder);

    }


}
