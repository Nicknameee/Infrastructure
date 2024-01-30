package io.management.ua.amqp.messages;

import io.management.ua.utility.enums.API;
import io.management.ua.utility.models.APICallbackModel;
import io.management.ua.utility.models.APIRequestModelParameters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIRequestModel {
    private API api;
    private APIRequestModelParameters data;
    private APICallbackModel apiCallbackModel;
}
