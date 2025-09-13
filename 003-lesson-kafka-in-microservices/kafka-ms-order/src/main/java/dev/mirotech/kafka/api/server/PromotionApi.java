package dev.mirotech.kafka.api.server;

import dev.mirotech.kafka.api.request.PromotionRequest;
import dev.mirotech.kafka.command.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PromotionApi {

    private final PromotionService promotionService;

    @PostMapping(
            value = "/api/promotion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> createPromotion(@RequestBody PromotionRequest request) {
        promotionService.createPromotion(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_PLAIN)
                .body(request.getPromotionCode());
    }

}
