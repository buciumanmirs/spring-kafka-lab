package dev.mirotech.kafka.broker.stream.promotion;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mirotech.kafka.broker.message.PromotionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Printed;

//@Component
@Slf4j
@RequiredArgsConstructor
public class PromotionUppercaseJsonStream {

    private final ObjectMapper objectMapper;

//    @Autowired
    void kstreamPromotionUppercase(StreamsBuilder builder) {
        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(Serdes.String(), Serdes.String()));
        var uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);
        uppercaseStream.to("t-commodity-promotion-uppercase");

        // useful for debugging, but it is better not to use this on production

        sourceStream.print(Printed.<String, String>toSysOut().withLabel("JSON Original Stream"));
        uppercaseStream.print(Printed.<String, String>toSysOut().withLabel("JSON Uppercase Stream"));

    }

    private String uppercasePromotionCode(String jsonString) {
        try {
            var promotion = objectMapper.readValue(jsonString, PromotionMessage.class);
            promotion.setPromotionCode(promotion.getPromotionCode().toUpperCase());
            return objectMapper.writeValueAsString(promotion);
        } catch (Exception e) {
            log.error("Error parsing promotion message: {}", jsonString, e);
            return "";
        }
    }
}
