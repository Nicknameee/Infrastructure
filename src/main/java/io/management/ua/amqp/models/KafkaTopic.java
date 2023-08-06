package io.management.ua.amqp.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KafkaTopic {
    public static final String CERTIFICATION_TOPIC = "certification";
}
