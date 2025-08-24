package dev.mirotech.kafka;

import dev.mirotech.kafka.entity.Employee;
import dev.mirotech.kafka.producer.Employee2JsonProducer;
import dev.mirotech.kafka.producer.EmployeeJsonProducer;
import dev.mirotech.kafka.producer.HelloKafkaAIProducer;
import dev.mirotech.kafka.producer.HelloKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@EnableScheduling
public class KafkaCoreProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaCoreProducerApplication.class, args);
    }


    @Autowired
    private Employee2JsonProducer employeeJsonProducer;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Employee " + i,
                    LocalDate.now().minusYears(20 + i));
            employeeJsonProducer.sendMessage(employee);
            System.out.println("Produced employee to Kafka: " + employee);
        }
    }
}
