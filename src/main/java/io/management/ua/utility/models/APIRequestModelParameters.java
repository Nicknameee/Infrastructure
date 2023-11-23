package io.management.ua.utility.models;

import lombok.Data;

import java.util.Map;

@Data
public class APIRequestModelParameters {
    private Map<String, Object> parameters;

    public Object get(String key) {
        return parameters.get(key);
    }
}
