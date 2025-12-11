package com.rabin.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "mySecretKey123"; // keep in env vars for prod
    private static final long EXPIRATION_TIME = 60 * 60 * 1000; // 1 hour in ms

    public static String generateToken(Long userId) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static Long validateTokenAndGetUserId(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return Long.parseLong(decodedJWT.getSubject());
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired JWT token");
        }
    }
}
