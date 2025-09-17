package dev.mirotech.kafka.util;

import dev.mirotech.kafka.broker.message.OrderMessage;
import dev.mirotech.kafka.broker.message.OrderPatternMessage;
import dev.mirotech.kafka.broker.message.OrderRewardMessage;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;

import java.time.OffsetDateTime;
import java.util.Base64;

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

    public static Predicate<String, OrderPatternMessage> isPlastic() {
        return (key, orderPatternMessage) -> orderPatternMessage.getItemName().toUpperCase().startsWith("PLASTIC");
    }

    public static Predicate<String, OrderMessage> isCheap() {
        return (key, orderMessage) -> orderMessage.getPrice() < 100;
    }

    public static KeyValueMapper<String, OrderMessage, String> generateStorageKey() {
        return (key, orderMessage) -> Base64.getEncoder().encodeToString(orderMessage.getOrderNumber().getBytes());
    }

}
