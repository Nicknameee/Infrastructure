package io.management.ua.utility.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class UserDetailsModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username" , nullable = false , unique = true)
    private String username;
    @Column(name = "email" , nullable = false , unique = true)
    private String email;
    @Column(name = "password" , nullable = false)
    private String password;
    @Column(name = "login_time" , nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime = new Date();
    @Column(name = "logout_time" , nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutTime = new Date(0);
    @Column(name = "role" , nullable = false)
    @Enumerated(EnumType.STRING)
    private UserSecurityRoles role = UserSecurityRoles.ROLE_CUSTOMER;
    @Column(name = "status" , nullable = false)
    @Enumerated(EnumType.STRING)
    private UserSecurityStatus status = UserSecurityStatus.DISABLED;
    @Column(name = "timezone", nullable = false)
    private String timezone;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return status == UserSecurityStatus.ENABLED;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return status == UserSecurityStatus.ENABLED;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return status == UserSecurityStatus.ENABLED;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return status == UserSecurityStatus.ENABLED;
    }
}
