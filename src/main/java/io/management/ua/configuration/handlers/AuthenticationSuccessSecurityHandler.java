package io.management.ua.configuration.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.management.ua.events.EventPublisher;
import io.management.ua.events.LoginEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationSuccessSecurityHandler implements AuthenticationSuccessHandler {
    private final EventPublisher<LoginEvent> loginEventEventPublisher;

    @Override
    public void onAuthenticationSuccess(@NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        loginEventEventPublisher.publishEvent(new LoginEvent((UserDetails) authentication.getPrincipal()));

        ObjectMapper jacksonMapper = new ObjectMapper();
        Map<String, Object> responseBodyMap = new HashMap<>();
        responseBodyMap.put("authenticated", true);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(jacksonMapper.writeValueAsString(responseBodyMap));
        response.getWriter().flush();
    }
}
