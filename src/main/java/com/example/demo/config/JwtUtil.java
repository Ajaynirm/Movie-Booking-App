package com.example.demo.config;

import com.example.demo.dto.LoginRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private  String SECRET_KEY;


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }

//    public String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token).getBody().getSubject();
//    }

//    public boolean validateToken(String token, LoginRequest loginRequest) {
//        String username = extractUsername(token);
//        return (username.equals(loginRequest.getEmail()) && !isTokenExpired(token));
//    }
//
//    private boolean isTokenExpired(String token) {
//        Date expiration = Jwts.parser().setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token).getBody().getExpiration();
//        return expiration.before(new Date());
//    }




    public String extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // your HS256 key
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("id", String.class); // Or "email"
    }
    public String extractName(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("name", String.class); // Or "email"
    }



    public String extractExpiryTime(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("exp", String.class); // Or "email"
    }


    public String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email", String.class);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
       return extractExpiry(token);
    }

    private boolean extractExpiry(String token){
        if(token==null || token.isEmpty()) return true;
        return Long.parseLong(extractExpiryTime(token))<System.currentTimeMillis();

    }





}


