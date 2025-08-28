package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class JwtDecoder {
    public String decodeJwt(String token, String key) throws Exception {
        String[] parts = token.split("\\.");
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> claims = mapper.readValue(payloadJson, Map.class);
        switch (key) {
            case "email":
                return claims.getOrDefault("email",null).toString();
            case "name":
                return claims.getOrDefault("name",null).toString();
            case "id": // Clerk user id is stored in "sub"
                return claims.getOrDefault("id",null).toString();
            case "exp":
                return claims.getOrDefault("exp",null).toString();
            default:
                return null;
        }
    }
    public boolean validateExpiry(String token) throws Exception{
        Long exp=Long.parseLong(decodeJwt(token,"exp"))*1000;
        return exp>=System.currentTimeMillis();
    }
}


