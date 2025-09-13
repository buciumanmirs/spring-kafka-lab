package dev.mirotech.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    final private KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

//    @Bean
    ProducerFactory<String, String> producerFactory(SslBundles sslBundles) {
        var props = kafkaProperties.buildProducerProperties(sslBundles);
        props.put(ProducerConfig.METRICS_SAMPLE_WINDOW_MS_CONFIG, "50000");
        return new DefaultKafkaProducerFactory<>(props);
    }

//    @Bean
    KafkaTemplate<String, String> kafkaTemplate(SslBundles sslBundles) {
        return new KafkaTemplate<>(producerFactory(sslBundles));
    }

}
