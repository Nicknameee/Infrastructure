package io.management.ua.configuration.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AuthorizationBasicRequestFilter extends OncePerRequestFilter {
    private final InMemoryUserDetailsManager basicUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        String authorizationHeaderValue = request.getHeader("Authorization");

        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Basic ")) {
            String authorizationToken = authorizationHeaderValue.substring(6);
            try {
                String[] credentials = extractAndDecodeHeader(authorizationToken);

                if (!credentials[0].isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = basicUserDetailsService.loadUserByUsername(credentials[0]);

                    if (userDetails.getPassword().equals(credentials[1])) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            } catch (IllegalArgumentException e) {
                logger.error("Unable to fetch HTTP Basic Token");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }

    private String[] extractAndDecodeHeader(String token) {
        String decodedString = new String(Base64.getDecoder().decode(token));

        int indexOfSplit = decodedString.indexOf(":");

        return new String[]{decodedString.substring(0, indexOfSplit), decodedString.substring(indexOfSplit + 1)};
    }
}
