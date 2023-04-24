package com.rasitesdmr.securityservice.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kafka.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
public class TokenCreation {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A713474370abcdefghijklmnOPQRSTUVWXYZ0";

    public String generateAccessToken(User user) {

        return Jwts.builder()
                .setSubject(String.format("%s , %s , %s , %s", user.getIdentityNumber(), user.getFirstName(), user.getLastName(), user.getEmail()))
                .setIssuer("SECURITY-SERVICE")
                .claim("roles", new ArrayList<>(user.getRoleList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getCustomExpirationTime())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            var a = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("[HATA : {}] - JWT'nin kullanım süresi doldu", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("[HATA : {}] - Token boş", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("[HATA : {}] - JWT geçersiz", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("[HATA : {}] - JWT desteklenmiyor", ex.getMessage());
        } catch (SignatureException ex) {
            log.error("[HATA : {}] - İmza doğrulama başarısız oldu" ,ex.getMessage());
        }

        return false;
    }

    public Date getCustomExpirationTime() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, 1440);
        return c.getTime();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
