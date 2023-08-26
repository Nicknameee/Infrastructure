package io.management.ua.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.management.ua.amqp.models.Message;
import io.management.ua.utility.UtilManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class KafkaValueSerializer implements Serializer<Message> {
    @Override
    public byte[] serialize(String s, Message message) {
        return processMessage(message);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Message message) {
        return processMessage(message);
    }

    private byte[] processMessage(Message message) {
        ObjectMapper objectMapper = UtilManager.objectMapper();

        try {
            return objectMapper.writeValueAsBytes(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SerializationException(String.format("Could not serialize message: %s", message));
        }
    }
}
