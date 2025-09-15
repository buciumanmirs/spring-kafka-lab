package dev.mirotech.kafka.broker.stream.promotion;

import dev.mirotech.kafka.broker.message.PromotionMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class PromotionUppercaseStringJsonStream {


    @Autowired
    void kstreamPromotionUppercase(StreamsBuilder builder){
        var jsonSerde = new JsonSerde<PromotionMessage>(PromotionMessage.class);

        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(Serdes.String(), jsonSerde));

        var uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);

        uppercaseStream.to("t-commodity-promotion-uppercase");

        sourceStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Original Stream"));
        uppercaseStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Uppercase Stream"));

    }

    private PromotionMessage uppercasePromotionCode(PromotionMessage message) {
        return new PromotionMessage(message.getPromotionCode().toUpperCase());
    }
}
