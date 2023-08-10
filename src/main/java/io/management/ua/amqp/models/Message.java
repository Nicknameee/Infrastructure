package io.management.ua.amqp.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.management.ua.utility.UtilManager;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Message implements Serializable {
    private String json;
    private String implementation;

    public Message(Object object) {
        try {
            this.json = UtilManager.objectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        this.implementation = object.getClass().getName();
    }
}
