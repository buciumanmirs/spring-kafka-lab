package dev.mirotech.kafka;

import dev.mirotech.kafka.entity.Invoice;
import dev.mirotech.kafka.producer.InvoiceProducer;
import dev.mirotech.kafka.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableScheduling
public class KafkaCoreProducerApplication implements CommandLineRunner {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceProducer invoiceProducer;


    public static void main(String[] args) {
        SpringApplication.run(KafkaCoreProducerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            var invoice = invoiceService.generateInvoice();
            if (i > 5) {
                invoice = new Invoice(invoice.invoiceNumber(), 0d, invoice.currency());
            }
            invoiceProducer.sendInvoice(invoice);
        }
    }


}
