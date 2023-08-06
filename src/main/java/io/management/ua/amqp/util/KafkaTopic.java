package io.management.ua.amqp.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KafkaTopic {
    public static final String AUTHENTICATION = "authentication";
    public static final String USER = "user";
    public static final String ORDER = "order";
    public static final String PAYMENT = "payment";
    public static final String API = "api";
}
