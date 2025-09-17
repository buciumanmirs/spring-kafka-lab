package dev.mirotech.kafka.broker.stream.commodity;

import dev.mirotech.kafka.broker.message.OrderMessage;
import dev.mirotech.kafka.broker.message.OrderPatternMessage;
import dev.mirotech.kafka.broker.message.OrderRewardMessage;
import dev.mirotech.kafka.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

//@Component
public class CommodityOneStream {

    @Autowired
    void kstreamCommodityTrading(StreamsBuilder builder) {
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        var maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(Serdes.String(), orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCardNumber);

        maskedCreditCardStream.mapValues(CommodityStreamUtil::convertToOrderPatternMessage)
                .to("t-commodity-order-pattern-one", Produced.with(Serdes.String(), orderPatternSerde));

        maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .mapValues(CommodityStreamUtil::convertToOrderRewardMessage)
                .to("t-commodity-order-reward-one", Produced.with(Serdes.String(), orderRewardSerde));

        maskedCreditCardStream.to("t-commodity-storage-one", Produced.with(Serdes.String(), orderSerde));
    }
}
