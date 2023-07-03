package io.management.ua.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@PropertySource("classpath:token.properties")
public class AuthorizationTokenUtility {
    private final Map<String, String> blacklistedTokens = new HashMap<>();
    @Value("${token.duration}")
    private int tokenValidityDuration;
    @Value("${token.secret}")
    private String tokenSecret;

    public void blacklistToken(String token) {
        String username = getUsernameFromToken(token);
        blacklistedTokens.put(username, token);
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

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        Date now = Date.from(LocalDateTime.now(Clock.systemDefaultZone()).toInstant(ZoneOffset.ofTotalSeconds(0)));
        return expiration.before(now) || blacklistedTokens.containsValue(token);
    }

    public String generateToken(UserDetails userDetails,
                                @NonNull HttpServletRequest request) {
        if (blacklistedTokens.containsKey(userDetails.getUsername())
                && !isTokenExpired(blacklistedTokens.get(userDetails.getUsername()))) {
            return blacklistedTokens.get(userDetails.getUsername());
        }
        Key key = getTokenKey();
        Map<String, String> claims = new HashMap<>();
        claims.put("User-Agent", request.getHeader("User-Agent"));
        claims.put("IP", request.getRemoteAddr());
        String token = Jwts.builder()
                           .setClaims(claims)
                           .setSubject(userDetails.getUsername())
                           .setIssuedAt(Date.from(LocalDateTime.now(Clock.systemDefaultZone())
                                   .toInstant(ZoneOffset.ofTotalSeconds(0))))
                           .setExpiration(Date.from(LocalDateTime.now(Clock.systemDefaultZone())
                                   .plusSeconds(tokenValidityDuration)
                                   .toInstant(ZoneOffset.ofTotalSeconds(0))))
                           .signWith(key)
                           .compact();
        blacklistedTokens.remove(userDetails.getUsername());
        return token;
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
                && !isTokenExpired(token) && !blacklistedTokens.containsValue(token);
    }

    private Key getTokenKey() {
        return new SecretKeySpec(tokenSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
    }
}
