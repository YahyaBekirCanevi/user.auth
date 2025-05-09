package com.canevi.user.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;
@Service
public class JwtService {
    private final Algorithm algorithm;

    public JwtService(@Value("${jwt.secret}") String secret) {
        algorithm = Algorithm.HMAC256(secret);
    }


    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("role", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        return getVerifier().verify(token).getSubject();
    }

    public String extractRole(String token) {
        return getVerifier().verify(token).getClaim("role").asString();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            DecodedJWT decodedJWT = getVerifier().verify(token);
            return decodedJWT.getSubject().equals(userDetails.getUsername())
                    && decodedJWT.getExpiresAt().after(new Date());
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private JWTVerifier getVerifier() {
        return JWT.require(algorithm).build();
    }
}


