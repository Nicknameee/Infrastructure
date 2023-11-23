package io.management.ua.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class ApplicationKafkaConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaTemplate<String, ?> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, ?> producerFactory() {
        return new DefaultKafkaProducerFactory<>(
                Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ProducerConfig.RETRIES_CONFIG, 0,
                        ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432,
                        ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 7000,
                        ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 3000,
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                        JsonDeserializer.TRUSTED_PACKAGES, "io.management.ua.amqp.messages"
                ));
    }

    @Bean
    public ConsumerFactory<String, ?> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, true,
                ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 1000,
                ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 3000,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000,
                JsonDeserializer.TRUSTED_PACKAGES, "io.management.ua.amqp.messages"
        ));
    }
}