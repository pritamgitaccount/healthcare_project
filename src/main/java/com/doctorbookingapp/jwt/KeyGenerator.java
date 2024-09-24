package com.doctorbookingapp.jwt;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    private static final int KEY_LENGTH = 32; // 256 bits

    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[KEY_LENGTH];
        secureRandom.nextBytes(keyBytes);
        String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Base64 Encoded Key: " + base64EncodedKey);
    }
}


