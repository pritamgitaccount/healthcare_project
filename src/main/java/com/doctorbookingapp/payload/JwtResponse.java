package com.doctorbookingapp.payload;

import lombok.*;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private long expiresIn;

    public JwtResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
