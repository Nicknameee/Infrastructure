package io.management.ua.utility.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpServletAddressesModel {
    private String scheme;
    private String server;
    private String port;
    private String origin;
}
