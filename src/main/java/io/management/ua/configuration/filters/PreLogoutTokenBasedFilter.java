package io.management.ua.configuration.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.management.ua.utility.AuthorizationTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PreLogoutTokenBasedFilter extends GenericFilterBean {
    private final UserDetailsService userDetailsService;
    private final AuthorizationTokenUtil authorizationTokenUtil;

    @Override
    public void doFilter(@NonNull ServletRequest servletRequest,
                         @NonNull ServletResponse servletResponse,
                         @NonNull FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getRequestURI().equals("/logout")) {
            String authorizationHeaderValue = request.getHeader("Authorization");

            if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer ")) {
                String authorizationToken = authorizationHeaderValue.substring(7);
                String username = authorizationTokenUtil.getUsernameFromToken(authorizationToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (authorizationToken.isEmpty() || !authorizationTokenUtil.validateToken(authorizationToken, userDetails, request)) {
                    processNonAuthenticatedExceptionResponse((HttpServletResponse) servletResponse);
                }
            } else {
                processNonAuthenticatedExceptionResponse((HttpServletResponse) servletResponse);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void processNonAuthenticatedExceptionResponse(@NonNull HttpServletResponse servletResponse) throws IOException {
        ObjectMapper jacksonMapper = new ObjectMapper();
        Map<String, Object> responseBodyMap = new HashMap<>();
        responseBodyMap.put("authenticated", false);
        responseBodyMap.put("exception", "You are not authenticated");
        servletResponse.setContentType("application/json");
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        servletResponse.getWriter().write(jacksonMapper.writeValueAsString(responseBodyMap));
        servletResponse.getWriter().flush();
    }
}
