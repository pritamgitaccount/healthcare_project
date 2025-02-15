package com.doctorbookingapp.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class TokenBlacklistService {

    private final Set<String> tokenBlacklist = new HashSet<>();

    public void addTokenToBlacklist(String token) {
        log.info("Blacklisting token : {}", token);
//        System.out.println("Blacklisting token: " + token);
        tokenBlacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }
}
