package com.doctorbookingapp.controller;


import com.doctorbookingapp.jwt.JwtService;
import com.doctorbookingapp.jwt.TokenBlacklistService;
import com.doctorbookingapp.payload.JwtResponse;
import com.doctorbookingapp.payload.SignInDto;
import com.doctorbookingapp.payload.SignUpDto;
import com.doctorbookingapp.service.CustomUserDetailsService;
import com.doctorbookingapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/auth/users")
//@SecurityRequirement(name="bearer-key")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    // private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;

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
    public ResponseEntity<String> logout(@RequestHeader(AUTHORIZATION) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.addTokenToBlacklist(token);  // Blacklist the token
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.badRequest().body("Invalid Authorization header");
    }


    //Login method
    // http://localhost:8080/auth/users/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInDto signInDto) throws Exception {
        // Step 1: Authenticate the user with username and password
        try {
            // Call the authenticate method to verify username and password
            authenticate(
                    signInDto.getUsername(),
                    signInDto.getPassword()
            );
        } catch (UsernameNotFoundException e) {
            // Log the error if the user is not found
            log.error(e.getMessage(), e);
            // Throw a custom exception if the user is not found
            throw new Exception("User not found");
        }

        // Step 2: After successful authentication, load the user's details
        // This fetches the user from the database based on the username
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(signInDto.getUsername());

        // Step 3: Generate a JWT token for the authenticated user
        // This token will be used for authorizing subsequent API requests
        String token = this.jwtService.generateToken(userDetails);

        // Step 4: Get the expiration time for the generated JWT token
        long expiresIn = this.jwtService.getExpirationTime(); // Retrieve the token expiration time

        // Step 5: Return the token and expiration time as part of the response
        // A custom response object (JwtResponse) is created containing the token and its validity duration
        return ResponseEntity.ok(new JwtResponse(token, expiresIn));
    }

    @GetMapping("/current-user-details")
    @Operation(
            summary = "Get Current User Details",
            description = "Retrieves the details of the currently logged-in user.",
            security = @SecurityRequirement(name = "bearerAuth"), // Add this line for Bearer token requirement
            responses = {
                    @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "User not authenticated")
            }
    )
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        // Assuming you have a custom method to convert UserDetails to a more detailed DTO
        return ResponseEntity.ok(userDetails);
    }


    /**
     * This method performs the authentication process.
     * It takes the username and password and verifies them using the AuthenticationManager.
     * If successful, it allows further actions; if not, it throws appropriate exceptions.
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            // The AuthenticationManager is used to verify the provided credentials.
            // It creates an authentication token based on the username and password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            // If the user account is disabled, throw an appropriate exception
            throw new Exception("User Disabled: " + e.getMessage());
        } catch (BadCredentialsException e) {
            // If the credentials (username/password) are invalid, throw an appropriate exception
            throw new Exception("Invalid Credentials: " + e.getMessage());
        }
    }
}
