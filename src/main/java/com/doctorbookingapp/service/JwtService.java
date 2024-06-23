package com.doctorbookingapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.doctorbookingapp.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration.time}")
    private long expirationTimeMillis; // Assuming this is in milliseconds

    private Algorithm algorithm;

    private static final String USER_NAME_CLAIM = "username";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(User user) {
        return JWT.create()
                .withClaim(USER_NAME_CLAIM, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUsernameFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim(USER_NAME_CLAIM).asString();
        } catch (JWTDecodeException e) {
            // Log or handle exception as needed
            return null;
        }
    }

    public boolean isValidToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
            return true; // Token is valid
        } catch (Exception e) {
            // Log or handle exception as needed
            return false; // Token is invalid
        }
    }
}
