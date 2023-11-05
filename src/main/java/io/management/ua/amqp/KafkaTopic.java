package io.management.ua.amqp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KafkaTopic {
    public static final String CERTIFICATION_REQUEST_TOPIC = "certification.request";
    public static final String CERTIFICATION_RESPONSE_TOPIC = "certification.response";
    public static final String MESSAGE_TOPIC = "message";
    public static final String REMOTE_SERVICE_REQUEST_TOPIC = "service";
}
