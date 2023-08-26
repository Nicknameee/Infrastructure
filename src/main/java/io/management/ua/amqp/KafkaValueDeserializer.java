package io.management.ua.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.management.ua.amqp.models.Message;
import io.management.ua.utility.UtilManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

@Slf4j
public class KafkaValueDeserializer implements Deserializer<Message> {
    @Override
    public Message deserialize(String topic, byte[] bytes) {
        return processMessage(bytes);
    }

    @Override
    public Message deserialize(String topic, Headers headers, byte[] bytes) {
        return processMessage(bytes);
    }

    private Message processMessage(byte[] bytes) {
        ObjectMapper objectMapper = UtilManager.objectMapper();
        if (bytes == null || bytes.length == 0) {
            return new Message();
        }

        try {
            return objectMapper.readValue(bytes, Message.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SerializationException(String.format("Could not deserialize message: %s", new String(bytes)));
        }
    }
}
