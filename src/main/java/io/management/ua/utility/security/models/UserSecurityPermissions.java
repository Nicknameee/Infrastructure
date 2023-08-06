package io.management.ua.utility.security.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public record UserSecurityPermissions() {
    public static final String userCreatePermission = "user::create";
    public static final String userReadPermission = "user::read";
    public static final String userUpdatePermission = "user::update";
    public static final String userDeletePermission = "user::delete";

    public static final String customerCreatePermission = "customer::create";
    public static final String customerReadPermission = "customer::read";
    public static final String customerUpdatePermission = "customer::update";
    public static final String customerDeletePermission = "customer::delete";

    public static final String managerCreatePermission = "manager::create";
    public static final String managerReadPermission = "manager::read";
    public static final String managerUpdatePermission = "manager::update";
    public static final String managerDeletePermission = "manager::delete";

    public static Set<GrantedAuthority> getUserAuthorities() {
        return Set.of(
                new SimpleGrantedAuthority(userCreatePermission),
                new SimpleGrantedAuthority(userReadPermission),
                new SimpleGrantedAuthority(userUpdatePermission),
                new SimpleGrantedAuthority(userDeletePermission));
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
