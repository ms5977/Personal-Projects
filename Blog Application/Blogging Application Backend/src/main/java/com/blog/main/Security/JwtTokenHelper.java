package com.blog.main.Security;


import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHelper  
{

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


	private String secret = "p2scHJd1m2dfjje3K4GQ5HLJ0k7Dfy2scHJd1m2dfjje3K4GQ5HLJ0k7Dfy";

    //retrieve username from jwt token
    public String extractUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date extractExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        
    
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

    	return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * JWT_TOKEN_VALIDITY)) // 5 minutes expiration time
                .signWith(getSigningKey())
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    } 
    private SecretKey getSigningKey() {
//        return Keys.hmacShaKeyFor(secret.getBytes());
    	byte[] decodekey = Base64.getDecoder().decode(secret);
    	return Keys.hmacShaKeyFor(decodekey);
    }
	    
	    
	    
	   
    
	    
	    
	    
	    
	    
 /*   
	    
	    
//	    -----------------------------------------------------------------------------------------
	    
	    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";
	    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	    private SecretKey getSigningKey() {
	        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	    }

	    public String extractUsername(String token) {
	        Claims claims = extractAllClaims(token);
	        return claims.getSubject();
	    }
	    public String generateToken(UserDetails userDetails) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, userDetails.getUsername());
	    }

	    public Date extractExpiration(String token) {
	        return extractAllClaims(token).getExpiration();
	    }
	    
	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	   
	    private Claims extractAllClaims(String token) {
	        return Jwts.parser()
	                .verifyWith(getSigningKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }

	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	   


	    private String createToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder()
	                .claims(claims)
	                .subject(subject)
	                .header().empty().add("typ","JWT")
	                .and()
	                .issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis() + 1000 * JWT_TOKEN_VALIDITY)) // 5 minutes expiration time
	                .signWith(getSigningKey())
	                .compact();
	    }

	    public Boolean validateToken(String token ,UserDetails userDetails) {
	    	  final String username = extractUsername(token);
	          return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	    */

}




