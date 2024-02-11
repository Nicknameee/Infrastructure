package io.management.ua.utility.service;

import io.management.ua.exceptions.NotFoundException;
import io.management.ua.utility.models.UserDetailsModel;
import io.management.ua.utility.models.UserSecurityRole;
import io.management.ua.utility.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsImplementationService implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsRepository.findAbstractUserModelByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username %s was not found", username)));
    }

    public UserSecurityRole getUserRoleById(Long id) {
        Optional<UserDetailsModel> userDetailsModel = userDetailsRepository.findById(id);

        return userDetailsModel.map(UserDetailsModel::getRole).orElse(null);

    }
}
