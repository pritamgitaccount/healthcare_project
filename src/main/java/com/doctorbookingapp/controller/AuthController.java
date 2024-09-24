package com.doctorbookingapp.controller;


import com.doctorbookingapp.jwt.JwtService;
import com.doctorbookingapp.jwt.TokenBlacklistService;
import com.doctorbookingapp.payload.JwtResponse;
import com.doctorbookingapp.payload.SignInDto;
import com.doctorbookingapp.payload.SignUpDto;
import com.doctorbookingapp.service.CustomUserDetailsService;
import com.doctorbookingapp.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth/users")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, TokenBlacklistService tokenBlacklistService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    // Create a new user
    // http://localhost:8080/auth/users/signup
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@Valid @RequestBody SignUpDto signUpDto, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            return new ResponseEntity<>(Objects.requireNonNull(result.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        try {
            // Delegate user creation to service
            userService.addUser(signUpDto);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    //Method for : Delete user
    // http://localhost:8080/auth/users/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User has deleted with Id : " + id, HttpStatus.OK);
    }

    //Method for : Logout user
    // http://localhost:8080/auth/users/logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.addTokenToBlacklist(token);  // Blacklist the token
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.badRequest().body("Invalid Authorization header");
    }



    //Login method
    // http://localhost:8080/auth/users/login
// In AuthController

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInDto signInDto) throws Exception {
        try {
            authenticate(signInDto.getUsername(), signInDto.getPassword());
        } catch (UsernameNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new Exception("User not found");
        }
        // Authenticate
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(signInDto.getUsername());
        String token = this.jwtService.generateToken(userDetails);
        long expiresIn = this.jwtService.getExpirationTime(); // Get the expiration time

        // Return the token and expiration time in the response
        return ResponseEntity.ok(new JwtResponse(token, expiresIn));
    }

    // Method to get details of the currently logged-in user using Principal
    //http://localhost:8080/auth/users/current-user-details
    @GetMapping("/current-user-details")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        // Assuming you have a custom method to convert UserDetails to a more detailed DTO
        return ResponseEntity.ok(userDetails);
    }



    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User Disabled" + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials" + e.getMessage());
        }
    }
}
