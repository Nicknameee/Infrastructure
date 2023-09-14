package io.management.ua.configuration.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.management.ua.events.publishers.EventPublisher;
import io.management.ua.events.LogoutEvent;
import io.management.ua.utility.AuthorizationTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationLogoutSecurityHandler implements LogoutSuccessHandler {
    private final UserDetailsService userDetailsService;
    private final AuthorizationTokenUtil authorizationTokenUtil;
    private final EventPublisher<LogoutEvent> logoutEventEventPublisher;

    @Override
    public void onLogoutSuccess(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                Authentication authentication) throws IOException {
        if (request.getRequestURI().equals("/logout")) {
            String authorizationHeaderValue = request.getHeader("Authorization");

            if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer ")) {
                String authorizationToken = authorizationHeaderValue.substring(7);
                String username = authorizationTokenUtil.getUsernameFromToken(authorizationToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (!authorizationToken.isEmpty()
                        && authorizationTokenUtil.validateToken(authorizationToken, userDetails, request)) {
                    authorizationTokenUtil.blacklistToken(authorizationToken);

                    logoutEventEventPublisher.publishEvent(new LogoutEvent(userDetails));

                    ObjectMapper jacksonMapper = new ObjectMapper();

                    Map<String, Object> responseBodyMap = new HashMap<>();
                    responseBodyMap.put("logout", true);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().write(jacksonMapper.writeValueAsString(responseBodyMap));
                    response.getWriter().flush();
                }
            }
        }
    }
}
