package io.management.ua.amqp.messages;

import io.management.ua.utility.enums.API;
import io.management.ua.utility.models.APIRequestModelParameters;
import lombok.Data;

@Data
public class APIRequestModel {
    private API api;
    private APIRequestModelParameters data;
}
