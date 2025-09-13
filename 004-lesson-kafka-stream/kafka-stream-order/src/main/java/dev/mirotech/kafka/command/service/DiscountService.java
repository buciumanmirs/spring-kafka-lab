package dev.mirotech.kafka.command.service;

import dev.mirotech.kafka.api.request.DiscountRequest;
import dev.mirotech.kafka.command.action.DiscountAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {


    private final DiscountAction action;

    public void createDiscount(DiscountRequest request) {
        var discountMessage = action.convertToMessage(request);
        action.sendToKafka(discountMessage);
    }

}
