package io.management.ua.utility.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WebSocketTopics {
    TELEGRAM_SUBSCRIPTION("/topic/telegram/subscription"),
    TRANSACTION_STATE("/topic/transaction/state");

    private final String topic;
}
