package dev.mirotech.kafka.command.service;

import dev.mirotech.kafka.api.request.PromotionRequest;
import dev.mirotech.kafka.command.action.PromotionAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionAction promotionAction;

    public void createPromotion(PromotionRequest request){
        var message = PromotionAction.toPromotionMessage(request);
        promotionAction.sendToKafka(message);
    }
}
