package dev.mirotech.kafka.service;

import dev.mirotech.kafka.entity.Invoice;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InvoiceService {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public Invoice generateInvoice(){
        var invoiceNumber = "INV"+ COUNTER.incrementAndGet();
        var amount = Math.round(ThreadLocalRandom.current().nextDouble(100, 1000)*100)/100.0;
        return new Invoice(invoiceNumber, amount, "USD");
    }

}
