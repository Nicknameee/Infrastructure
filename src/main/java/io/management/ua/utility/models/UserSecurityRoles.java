package io.management.ua.utility.models;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
public enum UserSecurityRoles {
    ROLE_SUPPORT(null),
    ROLE_OPERATOR(UserSecurityPermissions.getUserAuthorities()),
    ROLE_CUSTOMER(UserSecurityPermissions.getCustomerAuthorities()),
    ROLE_MANAGER(UserSecurityPermissions.getManagerAuthorities());

    private final Set<GrantedAuthority> authorities;

    UserSecurityRoles(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
