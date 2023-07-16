package io.management.ua.amqp.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message<T> implements Serializable {
    private T data;
}
