package io.management.ua.amqp;

import io.management.ua.amqp.models.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaTemplateTool<T, Y> {
    private final KafkaTemplate<T, Message<Y>> kafkaTemplate;

    public void send(String topic, Y message) {
        kafkaTemplate.send(topic, new Message<>(message));
    }
}