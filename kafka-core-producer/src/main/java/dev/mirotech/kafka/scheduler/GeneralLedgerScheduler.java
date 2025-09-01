package dev.mirotech.kafka.scheduler;

import dev.mirotech.kafka.producer.GeneralLedgerProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class GeneralLedgerScheduler {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    @Autowired
    private GeneralLedgerProducer producer;


    @Scheduled(fixedRate = 1000)
    public void schedule() {
        producer.sendGeneralLedgerMessage("Message " + COUNTER.incrementAndGet());
    }
}
