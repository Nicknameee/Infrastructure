package io.management.ua.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.management.ua.amqp.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class KafkaValueSerializer implements Serializer<Message> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String s, Message message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsBytes(message);
        } catch (Exception e) {
            throw new SerializationException(String.format("Could not serialize message: %s", message));
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Message data) {
        return new byte[0];
    }

    @Override
    public void close() {
    }
}
