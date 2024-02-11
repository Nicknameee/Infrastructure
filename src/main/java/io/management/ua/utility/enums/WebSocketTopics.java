package io.management.ua.utility.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WebSocketTopics {
    TELEGRAM_SUBSCRIPTION("/topic/telegram/subscription");

    private final String topic;
}
