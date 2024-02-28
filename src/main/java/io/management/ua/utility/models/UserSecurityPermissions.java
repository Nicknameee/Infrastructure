package io.management.ua.utility.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public record UserSecurityPermissions() {
    public static final String supportCreatePermission = "support::create";
    public static final String supportReadPermission = "support::read";
    public static final String supportUpdatePermission = "support::update";
    public static final String supportDeletePermission = "support::delete";
    public static final String operatorCreatePermission = "operator::create";
    public static final String operatorReadPermission = "operator::read";
    public static final String operatorUpdatePermission = "operator::update";
    public static final String operatorDeletePermission = "operator::delete";

    public static final String customerCreatePermission = "customer::create";
    public static final String customerReadPermission = "customer::read";
    public static final String customerUpdatePermission = "customer::update";
    public static final String customerDeletePermission = "customer::delete";

    public static final String managerCreatePermission = "manager::create";
    public static final String managerReadPermission = "manager::read";
    public static final String managerUpdatePermission = "manager::update";
    public static final String managerDeletePermission = "manager::delete";

    public static Set<GrantedAuthority> getSupportAuthorities() {
        return Set.of(
                new SimpleGrantedAuthority(supportCreatePermission),
                new SimpleGrantedAuthority(supportReadPermission),
                new SimpleGrantedAuthority(supportUpdatePermission),
                new SimpleGrantedAuthority(supportDeletePermission)
        );
    }
    public static Set<GrantedAuthority> getOperatorAuthorities() {
        return Set.of(
                new SimpleGrantedAuthority(operatorCreatePermission),
                new SimpleGrantedAuthority(operatorReadPermission),
                new SimpleGrantedAuthority(operatorUpdatePermission),
                new SimpleGrantedAuthority(operatorDeletePermission));
    }

    public static Set<GrantedAuthority> getCustomerAuthorities() {
        return Set.of(
                new SimpleGrantedAuthority(customerCreatePermission),
                new SimpleGrantedAuthority(customerReadPermission),
                new SimpleGrantedAuthority(customerUpdatePermission),
                new SimpleGrantedAuthority(customerDeletePermission));
    }

    public static Set<GrantedAuthority> getManagerAuthorities() {
        return Set.of(
                new SimpleGrantedAuthority(managerCreatePermission),
                new SimpleGrantedAuthority(managerReadPermission),
                new SimpleGrantedAuthority(managerUpdatePermission),
                new SimpleGrantedAuthority(managerDeletePermission));
    }
}
