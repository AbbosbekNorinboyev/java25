package uz.brb.java25.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.brb.java25.config.CustomUserDetailsService;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * tokenni yaratish va tekshirish
 */
@Component
@RequiredArgsConstructor
public class JWTUtil {
    private final CustomUserDetailsService userDetailsService;
    private final String KEY = "secretaesrdtfghjkllkjhugyufdrxfgvqwertyuiiiiopasdfghjklzxcvbnm";
    private final String SECRET_KEY = Base64.getEncoder().encodeToString(KEY.getBytes());

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        var roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet())
                .toString();
        claims.put("roles", roles);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10 * 24)) // 10 kun
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .setAllowedClockSkewSeconds(35)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}