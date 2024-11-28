package com.main.security;

import com.main.entity.Role;
import com.main.entity.UserCredential;
import com.main.exception.ResourceNotFoundException;
import com.main.repository.UserCredentialRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

@Component
public class JwtService {
    @Autowired
    private UserCredentialRepo userCredentialRepo;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public void validateToken(final String token) {
        Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        UserCredential userCredential = userCredentialRepo
                .findByUsername(username)
                .or(()->userCredentialRepo.findByEmail(username))
                .orElseThrow(()->new ResourceNotFoundException("username not found with given :"+username));

        Set<Role> roles = userCredential.getRoles();
        claims.put("roles", roles.stream().map(role -> role.getName()).toList());
        System.out.println("claims:" + claims);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey()).compact();
//               (getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    private SecretKey getSignKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(decode);
    }
}
