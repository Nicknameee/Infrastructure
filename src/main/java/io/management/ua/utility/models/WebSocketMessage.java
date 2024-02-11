package io.management.ua.utility.models;

import lombok.Data;

@Data
public class WebSocketMessage<T> {
    private T data;
    private String event;
}
