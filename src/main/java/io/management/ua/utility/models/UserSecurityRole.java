package io.management.ua.utility.models;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum UserSecurityRole {
    ROLE_VENDOR(UserSecurityPermissions.getVendorAuthorities()),
    ROLE_SUPPORT(UserSecurityPermissions.getSupportAuthorities()),
    ROLE_OPERATOR(UserSecurityPermissions.getOperatorAuthorities()),
    ROLE_CUSTOMER(UserSecurityPermissions.getCustomerAuthorities()),
    ROLE_MANAGER(UserSecurityPermissions.getManagerAuthorities());

    private final Set<GrantedAuthority> authorities;

    UserSecurityRole(Set<GrantedAuthority> authorities) {
        this.authorities = new HashSet<>(authorities);
        this.authorities.add(new SimpleGrantedAuthority(this.name()));
    }
}
