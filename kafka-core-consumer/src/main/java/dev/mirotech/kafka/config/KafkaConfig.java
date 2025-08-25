package dev.mirotech.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class KafkaConfig {

    final private KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    ConsumerFactory<Object, Object> consumerFactory(SslBundles sslBundles) {
        var props = kafkaProperties.buildConsumerProperties(sslBundles);
        props.put(ConsumerConfig.METRICS_SAMPLE_WINDOW_MS_CONFIG, "400000");
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
