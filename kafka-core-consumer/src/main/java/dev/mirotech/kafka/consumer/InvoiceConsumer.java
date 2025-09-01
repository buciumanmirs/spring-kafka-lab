package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Invoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "t-invoice", concurrency = "2", containerFactory = "invoiceDltKafkaListenerContainerFactory")
    public void consumeInvoice(String message) throws Exception {
        var invoice = objectMapper.readValue(message, Invoice.class);
        if (invoice.amount() < 1) {
            throw new IllegalArgumentException("Amount must be greater than zero: " + invoice.amount());
        }
        log.info("Received invoice: {}", invoice);
    }
}
