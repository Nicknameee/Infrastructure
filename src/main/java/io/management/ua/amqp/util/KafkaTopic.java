package io.management.ua.amqp.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KafkaTopic {
    public static final String AUTHENTICATION = "authentication";
    public static final String USER = "user";
}
