package dev.mirotech.kafka.error.handler;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "foodOrderErrorHandler")
public class FoodOrderErrorHandler implements ConsumerAwareListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        log.error("FoodOrder error while processing message: {}", message, exception);

        // the purpose is throw a specific exception -> will be caught by the CommonKafkaErrorHandler.handleOtherException()
        if (exception.getCause() instanceof IllegalArgumentException){
            throw exception;
        }
        return null;
    }
}
