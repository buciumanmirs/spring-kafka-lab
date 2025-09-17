package dev.mirotech.kafka.util;

import dev.mirotech.kafka.broker.message.OrderMessage;

public class CommodityStreamUtil {


    public static OrderMessage maskCreditCardNumber(OrderMessage orderMessage) {
        var crediCardNumber = orderMessage.getCreditCardNumber();
        var maskedCreditCardNumber =  crediCardNumber == null? "null" : "****_****_****_" + crediCardNumber.substring(crediCardNumber.length() - 4);
        return new OrderMessage(
                orderMessage.getOrderLocation(),
                orderMessage.getOrderNumber(),
                maskedCreditCardNumber,
                orderMessage.getOrderDateTime(),
                orderMessage.getItemName(),
                orderMessage.getPrice(),
                orderMessage.getQuantity()
        );
    }


}
