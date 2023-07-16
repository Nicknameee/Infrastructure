package io.management.ua.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.management.ua.amqp.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

@Slf4j
public class KafkaValueDeserializer implements Deserializer<Message> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Message deserialize(String topic, byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(bytes, Message.class);
        } catch (Exception e) {
            throw new SerializationException(String.format("Could not deserialize message: %s", new String(bytes)));
        }
    }

    @Override
    public void close() {
    }
}
