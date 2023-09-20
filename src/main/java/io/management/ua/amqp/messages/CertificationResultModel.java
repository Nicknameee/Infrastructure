package io.management.ua.amqp.messages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResultModel {
    @NotBlank(message = "Identifier can not be blank")
    private String identifier;
    private boolean certified;
}
