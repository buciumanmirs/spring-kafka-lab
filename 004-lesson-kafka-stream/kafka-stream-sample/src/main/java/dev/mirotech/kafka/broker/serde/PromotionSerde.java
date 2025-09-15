package dev.mirotech.kafka.broker.serde;

import dev.mirotech.kafka.broker.message.PromotionMessage;

public class PromotionSerde extends CustomJsonSerde<PromotionMessage>{

    public PromotionSerde(){
        super(new CustomJsonSerializer<>(), new CustomJsonDeserializer<>(PromotionMessage.class));
    }

}
