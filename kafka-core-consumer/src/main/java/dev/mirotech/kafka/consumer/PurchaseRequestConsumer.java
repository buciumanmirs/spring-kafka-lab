package dev.mirotech.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import dev.mirotech.kafka.entity.PurchaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
//@RequiredArgsConstructor
public class PurchaseRequestConsumer {

    private final ObjectMapper objectMapper;


    private final Cache<String, Boolean> cache;


    public PurchaseRequestConsumer(ObjectMapper objectMapper,
                                   @Qualifier("cachePurchaseRequest") Cache<String, Boolean> cache) {
        this.objectMapper = objectMapper;
        this.cache = cache;
    }

    private boolean isExistsInCache(String requestNumber) {
        return cache.getIfPresent(requestNumber) != null;
    }

    @KafkaListener(topics = "t-purchase-request")
    public void consumePurchaseRequest(String message){
        try {
            var purchaseRequest = objectMapper.readValue(message, PurchaseRequest.class);

            if(isExistsInCache(purchaseRequest.getRequestNumber())){
                log.info("Purchase request already exists in cache: {}", purchaseRequest);
                return;
            }

            log.info("Purchase request received: {}", purchaseRequest);
            cache.put(purchaseRequest.getRequestNumber(), true);

        }catch (Exception e){
            log.error("Error while processing message: {}", message, e);
        }
    }
}
