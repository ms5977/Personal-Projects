package com.main.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;

@Component
public class JwtUtil {
    public static final String SECRET="5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public void validateToken(final  String token){
        Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
    }
    private SecretKey getSignKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(decode);
    }
    public List<String>extractRoles(String token){
        Claims claims = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
        List roles = claims.get("roles", List.class);
        System.out.println("roles"+roles);
        return roles;
    }
}
