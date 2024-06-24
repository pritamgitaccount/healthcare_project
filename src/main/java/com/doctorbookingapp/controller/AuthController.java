package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.payload.JWTAuthResponse;
import com.doctorbookingapp.payload.SignInDto;
import com.doctorbookingapp.payload.SignUpDto;
import com.doctorbookingapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // http://localhost:8080/api/users/signup
    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@Valid @RequestBody SignUpDto signUpDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(Objects.requireNonNull(result.getFieldError())
                    .getDefaultMessage(), HttpStatus.BAD_REQUEST); // Changed to BAD_REQUEST
        }
        SignUpDto dto = userService.addUser(signUpDto);
        if (dto != null) {
            return new ResponseEntity<>("SignUp successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT); // Handle user already exists
        }
    }

    // http://localhost:8080/api/users/loginUser
    @PostMapping("/loginUser")
    public ResponseEntity<?> login(@RequestBody SignInDto signInDto) {
        String verifiedToken = userService.verifyLogin(signInDto);
        if (verifiedToken != null) {
            JWTAuthResponse response = new JWTAuthResponse();
            response.setAccessToken(verifiedToken);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED); // Provide error message
    }

    // http://localhost:8080/api/users/profile
    @GetMapping("/profile")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
