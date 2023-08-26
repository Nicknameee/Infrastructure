package io.management.ua.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@PropertySource("classpath:token.properties")
public class AuthorizationTokenUtil {
    private final Map<String, Set<String>> blacklistedTokens = new HashMap<>();
    @Value("${token.duration:3600}")
    private int tokenValidityDuration;
    @Value("${token.secret}")
    private String tokenSecret;

    @Scheduled(initialDelayString = "${token.duration:3600}", fixedRateString = "${token.duration:3600}", timeUnit = TimeUnit.SECONDS)
    public void clearTokens() {
        for (Map.Entry<String, Set<String>> entry : blacklistedTokens.entrySet()) {
            Set<String> validTokens = new HashSet<>();

            for (String token : entry.getValue()) {

                if (checkTokenExpiration(token)) {
                    validTokens.add(token);
                }
            }

            blacklistedTokens.put(entry.getKey(), validTokens);
        }
    }

    public void blacklistToken(String token) {
        String username = getUsernameFromToken(token);
        blacklistedTokens.putIfAbsent(username, new HashSet<>());
        blacklistedTokens.get(username).add(token);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        Key key = getTokenKey();

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private boolean checkTokenExpiration(String token) {
        Date now = Date.from(LocalDateTime.now(Clock.systemUTC()).toInstant(ZoneOffset.ofTotalSeconds(0)));
        Date expiration = getExpirationDateFromToken(token);

        return expiration.after(now);
    }

    public String generateToken(@NonNull UserDetails userDetails,
                                @NonNull HttpServletRequest request) {
        Map<String, String> claims = new HashMap<>();
        claims.put("User-Agent", request.getHeader("User-Agent"));
        claims.put("IP", request.getRemoteAddr());

        return Jwts.builder()
                           .setClaims(claims)
                           .setSubject(userDetails.getUsername())
                           .setIssuedAt(Date.from(ZonedDateTime.now(Clock.systemUTC())
                                   .toInstant()))
                           .setExpiration(Date.from(ZonedDateTime.now(Clock.systemUTC())
                                   .plusSeconds(tokenValidityDuration)
                                   .toInstant()))
                           .signWith(getTokenKey())
                           .compact();
    }

    public boolean validateToken(String token,
                                 UserDetails userDetails,
                                 @NonNull HttpServletRequest request) {
        String username = getUsernameFromToken(token);
        Claims claims = getAllClaimsFromToken(token);
        String userAgent = (String) claims.get("User-Agent");
        String address = (String) claims.get("IP");

        return username.equals(userDetails.getUsername())
                && userAgent.equals(request.getHeader("User-Agent"))
                && address.equals(request.getRemoteAddr())
                && checkTokenExpiration(token)
                && !checkTokenBlacklist(token);
    }

    private Key getTokenKey() {
        return new SecretKeySpec(tokenSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
    }

    private boolean checkTokenBlacklist(String token) {
        String username = getUsernameFromToken(token);

        if (blacklistedTokens.containsKey(username)) {
            return blacklistedTokens.get(username).contains(token);
        }

        return false;
    }
}
