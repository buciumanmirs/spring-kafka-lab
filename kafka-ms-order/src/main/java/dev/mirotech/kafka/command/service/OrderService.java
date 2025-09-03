package dev.mirotech.kafka.command.service;

import dev.mirotech.kafka.api.request.OrderRequest;
import dev.mirotech.kafka.broker.message.OrderMessage;
import dev.mirotech.kafka.command.action.OrderAction;
import dev.mirotech.kafka.entiy.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderAction action;

    public String saveOrder(OrderRequest request){
        Order orderEntity = action.convertToOrder(request);

        action.saveToDatabase(orderEntity);

        orderEntity.getOrderItems().forEach( item ->{
            OrderMessage orderMessage = action.convertToOrderMessage(item);
            action.sendToKafka(orderMessage);
        });
        return orderEntity.getOrderNumber();
    }
}
