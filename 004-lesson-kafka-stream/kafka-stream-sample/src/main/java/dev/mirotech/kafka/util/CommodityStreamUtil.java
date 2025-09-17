package dev.mirotech.kafka.util;

import dev.mirotech.kafka.broker.message.OrderMessage;
import dev.mirotech.kafka.broker.message.OrderPatternMessage;
import dev.mirotech.kafka.broker.message.OrderRewardMessage;
import org.apache.kafka.streams.kstream.Predicate;

import java.time.OffsetDateTime;

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

    public static OrderPatternMessage convertToOrderPatternMessage(OrderMessage orderMessage) {
        String itemName = orderMessage.getItemName();
        long totalItemAmount = (long) orderMessage.getPrice() * orderMessage.getQuantity();
        OffsetDateTime orderDateTime = orderMessage.getOrderDateTime();
        String orderLocation = orderMessage.getOrderLocation();
        String orderNumber = orderMessage.getOrderNumber();

        return new OrderPatternMessage(itemName, totalItemAmount, orderDateTime, orderLocation, orderNumber);
    }

    public static OrderRewardMessage convertToOrderRewardMessage(OrderMessage orderMessage) {
        String orderLocation = orderMessage.getOrderLocation();
        String orderNumber = orderMessage.getOrderNumber();
        OffsetDateTime orderDateTime = orderMessage.getOrderDateTime();
        String itemName = orderMessage.getItemName();
        int price = orderMessage.getPrice();
        int quantity = orderMessage.getQuantity();

        return new OrderRewardMessage(orderLocation, orderNumber, orderDateTime, itemName, price, quantity);
    }

    public static Predicate<String, OrderMessage> isLargeQuantity() {
        return (key, orderMessage) -> orderMessage.getQuantity() > 200;
    }
}
