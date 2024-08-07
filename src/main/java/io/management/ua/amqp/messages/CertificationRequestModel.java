package io.management.ua.amqp.messages;

import io.management.ua.utility.models.CertificationAction;
import io.management.ua.utility.models.HttpServletAddressesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRequestModel {
    @NotBlank(message = "Identifier can not be blank")
    private String identifier;
    private CertificationAction action;
    private HttpServletAddressesModel httpServletAddressesModel;
}
