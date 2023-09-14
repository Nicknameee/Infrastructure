package io.management.ua.amqp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KafkaTopic {
    public static final String CERTIFICATION_REQUEST_TOPIC = "certification_request";
    public static final String CERTIFICATION_RESPONSE_TOPIC = "certification_response";
    public static final String MESSAGE_TOPIC = "message";

}
