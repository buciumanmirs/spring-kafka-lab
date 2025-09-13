package dev.mirotech.kafka.broker.message.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountMessage {

	private String discountCode;

	private int discountPercentage;

}
