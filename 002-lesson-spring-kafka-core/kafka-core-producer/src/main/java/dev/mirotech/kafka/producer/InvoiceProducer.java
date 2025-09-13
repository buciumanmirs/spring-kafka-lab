package dev.mirotech.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.entity.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InvoiceProducer {

    final private KafkaTemplate<String, String> kafkaTemplate;

    final private ObjectMapper objectMapper;

    public void sendInvoice(Invoice invoice) {
        try {
            var invoiceJson = objectMapper.writeValueAsString(invoice);
            kafkaTemplate.send("t-invoice", (int) invoice.amount() % 2, invoice.invoiceNumber(), invoiceJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
