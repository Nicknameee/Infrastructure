package io.management.ua.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.management.ua.amqp.models.Message;
import io.management.ua.utility.UtilManager;

public class KafkaValueParser {
    public static Object parseObject(Message message) {
        try {
            Class<?> jsonClass = Class.forName(message.getImplementation());

            return UtilManager.objectMapper().readValue(message.getJson(), jsonClass);
        } catch (ClassNotFoundException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
