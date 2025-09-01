package dev.mirotech.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GeneralLedgerConsumer {


    @KafkaListener(topics = "t-general-ledger", id ="consumer-ledger-one")
    public void consumeLedgerOne(String message) {
        log.info("consumeLedgerOne() consumed message: {}", message);
    }

    @KafkaListener(topics = "t-general-ledger")
    public void consumeLedgerTwo(String message) {
        log.info("consumeLedgerTwo() consumed message: {}", message);
    }
}

