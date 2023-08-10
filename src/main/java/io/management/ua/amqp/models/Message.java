package io.management.ua.amqp.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.management.ua.utility.UtilManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private String json;
    private String implementation;

    public Message(Object object) throws JsonProcessingException {
        this.json = UtilManager.objectMapper().writeValueAsString(object);
        this.implementation = object.getClass().getName();
    }
}
