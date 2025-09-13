package dev.mirotech.kafka.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class GeneralLedgerScheduler {

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Scheduled(cron = "0 28 15 * * *")
    public void pause(){
        log.info("Pausing General Ledger Consumer");
        Objects.requireNonNull(registry.getListenerContainer("consumer-ledger-one")).pause();
    }

    @Scheduled(cron = "0 30 15 * * *")
    public void resume(){
        log.info("Resume General Ledger Consumer");
        Objects.requireNonNull(registry.getListenerContainer("consumer-ledger-one")).resume();

    }
}
