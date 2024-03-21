package io.management.users.service;

import io.management.ua.annotations.Export;
import io.management.ua.exceptions.NotFoundException;
import io.management.ua.utility.models.UserSecurityRole;
import io.management.users.models.UserDetailsModel;
import io.management.users.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("userDetailsAPIService")
@RequiredArgsConstructor
@Slf4j
@Primary
public class UserDetailsImplementationService implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userDetailsRepository.findUserDetailsByLogin(login)
                .orElseThrow(() -> new NotFoundException(String.format("User with login %s was not found", login)));
    }

    @Export
    public UserSecurityRole getUserRoleById(Long id) {
        Optional<UserDetailsModel> userDetailsModel = userDetailsRepository.findById(id);

        return userDetailsModel.map(UserDetailsModel::getRole).orElse(null);

    }
}
