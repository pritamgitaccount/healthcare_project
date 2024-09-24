package com.doctorbookingapp.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlacklistService {

    private static final Logger logger = LoggerFactory.getLogger(TokenBlacklistService.class);
    private final Set<String> tokenBlacklist = new HashSet<>();

    public void addTokenToBlacklist(String token) {
        logger.info("Blacklisting token : {}", token);
//        System.out.println("Blacklisting token: " + token);
        tokenBlacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }
}
