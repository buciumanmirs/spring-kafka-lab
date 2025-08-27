package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import dev.mirotech.kafka.entity.PaymentRequest;
import dev.mirotech.kafka.entity.PurchaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@RequiredArgsConstructor
public class PaymentRequestConsumer {

    private final ObjectMapper objectMapper;


    private final Cache<String, Boolean> cache;


    @Autowired
    public PaymentRequestConsumer(ObjectMapper objectMapper,
                                  @Qualifier("cachePaymentRequest") Cache<String, Boolean> cache) {
        this.objectMapper = objectMapper;
        this.cache = cache;
    }

    private boolean isExistsInCache(String requestNumber) {
        return cache.getIfPresent(requestNumber) != null;
    }

    @KafkaListener(topics = "t-payment-request")
    public void consumePurchaseRequest(String message){
        try {
            var purchaseRequest = objectMapper.readValue(message, PaymentRequest.class);
            var paymentKey = purchaseRequest.calculateSha256();
            if(isExistsInCache(paymentKey)){
                log.info("Purchase request already exists in cache: {}", purchaseRequest);
                return;
            }

            log.info("Purchase request received: {}", purchaseRequest);
            cache.put(paymentKey, true);

        }catch (Exception e){
            log.error("Error while processing message: {}", message, e);
        }
    }
}
