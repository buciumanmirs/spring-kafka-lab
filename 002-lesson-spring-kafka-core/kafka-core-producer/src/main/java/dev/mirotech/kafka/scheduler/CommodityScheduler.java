package dev.mirotech.kafka.scheduler;

import dev.mirotech.kafka.entity.Commodity;
import dev.mirotech.kafka.producer.CommodityProducer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

//@Component
public class CommodityScheduler {

    final private CommodityProducer commodityProducer;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:8080") // Adaugă URL-ul de bază pentru API
            .defaultHeader("Accept", "application/json")
            .build();


    public CommodityScheduler(CommodityProducer commodityProducer) {
        this.commodityProducer = commodityProducer;
    }

    @Scheduled(fixedRate = 5000)
    public void fetchAndSendCommodities() {
        fetchCommodities().forEach(
                commodityProducer::sendMessage);
    }

    private List<Commodity> fetchCommodities() {
        return restClient.get()
                .uri("/api/commodity/v1/all")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

}
