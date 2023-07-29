package io.management.ua.amqp.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaTopic {
    AUTHENTICATION("authentication");

    private final String topic;
}
