package dev.mirotech.kafka.broker.message.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReplyMessage {

    private String replyMessage;
}
