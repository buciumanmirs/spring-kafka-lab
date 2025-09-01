package dev.mirotech.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneralLedgerProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;


    public void sendGeneralLedgerMessage(String message) {
        kafkaTemplate.send("t-general-ledger", message);
    }
}
