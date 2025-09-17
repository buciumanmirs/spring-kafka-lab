package dev.mirotech.kafka.command.action;

import dev.mirotech.kafka.api.request.OrderRequest;
import dev.mirotech.kafka.broker.message.OrderMessage;
import dev.mirotech.kafka.broker.producer.OrderProducer;
import dev.mirotech.kafka.entiy.Order;
import dev.mirotech.kafka.entiy.OrderItem;
import dev.mirotech.kafka.repository.OrderItemRepository;
import dev.mirotech.kafka.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderAction{

    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    public Order convertToOrder(OrderRequest request) {
        var order = new Order();
        order.setOrderLocation(request.getOrderLocation());
        order.setCreditCardNumber(request.getCreditCardNumber());
        order.setOrderDateTime(OffsetDateTime.now());
        order.setOrderNumber(UUID.randomUUID().toString());

        var orderItems = request.getItems().stream().map(item ->
        {
           var orderItem = new OrderItem();
           orderItem.setItemName(item.getItemName());
           orderItem.setPrice(item.getPrice());
           orderItem.setQuantity(item.getQuantity());
           orderItem.setOrder(order);
           return orderItem;
        }).toList();

        order.setOrderItems(orderItems);
        return order;
    }

    public void saveToDatabase(Order orderEntity) {
        orderRepository.save(orderEntity);
        orderItemRepository.saveAll(orderEntity.getOrderItems());
    }

    public OrderMessage convertToOrderMessage(OrderItem item) {
        var orderMessage = new OrderMessage();
        orderMessage.setItemName(item.getItemName());
        orderMessage.setPrice(item.getPrice());
        orderMessage.setQuantity(item.getQuantity());
        orderMessage.setCreditCardNumber(item.getOrder().getCreditCardNumber());
        orderMessage.setOrderNumber(item.getOrder().getOrderNumber());
        orderMessage.setOrderDateTime(item.getOrder().getOrderDateTime());
        orderMessage.setOrderLocation(item.getOrder().getOrderLocation());
        return orderMessage;
    }

    public void sendToKafka(OrderMessage orderMessage) {
        orderProducer.sendOrder(orderMessage);
    }
}
