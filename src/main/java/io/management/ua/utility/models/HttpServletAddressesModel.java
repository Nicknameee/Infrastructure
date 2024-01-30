package io.management.ua.utility.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpServletAddressesModel {
    private String scheme;
    private String server;
    private String port;
    private String origin;
}
