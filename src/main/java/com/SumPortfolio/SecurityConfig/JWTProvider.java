package com.SumPortfolio.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTProvider {

    static SecretKey secretKey = Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());

//    String authorities = String.valueOf(claims.get("authorities"));

    public static String generateToken(Authentication auth){
        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+84600000))
                .claim("email", auth.getName())
                .signWith(secretKey)
                .compact();

        return jwt;
    }

    public static String getEmailFromToken(String jwt){
        Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJwt(jwt).getBody();

        return String.valueOf(claims.get("emails"));
    }
}
