package io.management.ua.utility.models;

import lombok.Data;

@Data
public class APICallbackModel {
    private String targetTopic;
    private Class<?> targetResponseType;
}
