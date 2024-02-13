package io.management.ua.utility.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WebSocketTopics {
    TELEGRAM_SUBSCRIPTION("/topic/telegram/subscription");

    private final String topic;
}
