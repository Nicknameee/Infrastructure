package io.management.ua.amqp.models.messages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResultModel {
    private String identifier;
    private boolean certified;
}
