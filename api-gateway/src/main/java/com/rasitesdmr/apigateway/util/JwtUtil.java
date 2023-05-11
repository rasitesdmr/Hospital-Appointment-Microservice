package com.rasitesdmr.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {


    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A713474370abcdefghijklmnOPQRSTUVWXYZ0";


    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public String getIdentityNumberFromToken(final String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        String subject = claims.getSubject();
        String[] parts = subject.split(",");
        if (parts.length >= 2) {
            return parts[0].trim();
        }
        return null;
    }

    public Claims getAllClaimsFromToken(String token) {
      return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }



    public List<String> getRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        List<Map<String, String>> roleMaps = claims.get("roles", List.class);

        if (roleMaps == null || roleMaps.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> roles = roleMaps.stream()
                .map(roleMap -> (String) roleMap.get("name"))
                .collect(Collectors.toList());

        return roles;
    }



    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
