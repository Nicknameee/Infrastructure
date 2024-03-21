package io.management.ua.utility.models;

import lombok.Data;

@Data
public class DefaultSpringSecurityUser {
    private String name;
    private String password;
    private String[] roles;
}
