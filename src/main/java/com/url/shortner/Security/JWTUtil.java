package com.url.shortner.Security;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.url.shortner.Service.UserDetailImp;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String JwtSecret;
    @Value("${jwt.expiration}")
    private int JwtExpirationMs;

    public static String getJWTtoken(HttpServletRequest request) {
        String Bearertoken = request.getHeader("Authorization");
        if (Bearertoken != null && Bearertoken.startsWith("Bearer ")) {
            return Bearertoken.substring(7);
        }
        return null;
    }

    public String generateToken(UserDetailImp userDetailImp) {
        String username = userDetailImp.getUsername();

        String role = userDetailImp.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtSecret));
    }
}
