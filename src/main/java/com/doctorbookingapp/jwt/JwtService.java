package com.doctorbookingapp.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration; // in milliseconds

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    /*
    The use of generics (<T>) makes the extractClaim method flexible, allowing it to return different types of claims based on the
     provided claimsResolver function.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }



    public String generateToken(UserDetails userDetails) {
        logger.info("Generating token for user: {}", userDetails.getUsername());
        logger.debug("JWT Expiration Time: {} ms", jwtExpiration);
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        logger.info("Building token for user: {}", userDetails.getUsername());
        logger.debug("Token will expire in {} ms", expiration);

        String token = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        logger.info("Token generated successfully for user: {}", userDetails.getUsername());
        logger.debug("Generated token: {}", token);

        return token;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        logger.info("Validating token for user: {}", username);
        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        logger.debug("Is token valid: {}", isValid);
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        boolean expired = expiration.before(new Date());
        logger.debug("Token expiration date: {}, Current time: {}, Is expired: {}", expiration, new Date(), expired);
        return expired;
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Error while extracting claims from token", e);
            throw e;  // Re-throw or handle as per your logic
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Add the missing getExpirationTime() method with logging
    public long getExpirationTime() {
        logger.info("Retrieving JWT expiration time");
        logger.debug("JWT Expiration Time: {} ms", jwtExpiration);
        return jwtExpiration;
    }

}
