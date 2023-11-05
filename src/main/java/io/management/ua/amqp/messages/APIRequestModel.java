package io.management.ua.amqp.messages;

import io.management.ua.utility.enums.API;
import lombok.Data;

@Data
public class APIRequestModel<T> {
    private API api;
    private T data;
}
