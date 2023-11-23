package io.management.ua.utility.models;

import lombok.Data;

import java.util.HashMap;

@Data
public class APIRequestModelParameters {
    private HashMap<String, Object> parameters;

    public Object get(String key) {
        return parameters.get(key);
    }
}
