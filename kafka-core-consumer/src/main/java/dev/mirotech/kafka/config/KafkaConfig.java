package dev.mirotech.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import dev.mirotech.kafka.entity.CarLocation;
import dev.mirotech.kafka.entity.PaymentRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

@Configuration
public class KafkaConfig {

    final private KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    //    @Bean
    ConsumerFactory<Object, Object> consumerFactory(SslBundles sslBundles) {
        var props = kafkaProperties.buildConsumerProperties(sslBundles);
        props.put(ConsumerConfig.METRICS_SAMPLE_WINDOW_MS_CONFIG, "400000");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "locationFarContainerFactory")
    ConcurrentKafkaListenerContainerFactory<Object, Object> locationFarContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            SslBundles sslBundles,
            ObjectMapper objectMapper) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory(sslBundles));
        factory.setRecordFilterStrategy(consumerRecord -> {
            try {
                var carLocation = objectMapper.readValue(consumerRecord.value().toString(), CarLocation.class);
                return carLocation.getDistance() < 100;
            } catch (Exception e) {
                return false;
            }
        });
        return factory;
    }

    @Bean(name = "paymentRequest2ContainerFactory")
    ConcurrentKafkaListenerContainerFactory<Object, Object> paymentRequestContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            SslBundles sslBundles,
            ObjectMapper objectMapper,
            @Qualifier("cachePaymentRequest") Cache<String, Boolean> cachePaymentRequest
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory(sslBundles));
        factory.setRecordFilterStrategy(consumerRecord -> {
            try {
                var paymentRequest = objectMapper.readValue(consumerRecord.value().toString(), PaymentRequest.class);
                var cacheKey = paymentRequest.calculateSha256();
                return cachePaymentRequest.getIfPresent(cacheKey) != null;

            } catch (Exception e) {
                return false;
            }
        });
        return factory;
    }
}
