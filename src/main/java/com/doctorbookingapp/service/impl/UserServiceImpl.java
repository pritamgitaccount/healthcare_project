package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.payload.SignInDto;
import com.doctorbookingapp.payload.SignUpDto;
import com.doctorbookingapp.repository.UserRepository;
import com.doctorbookingapp.service.JwtService;
import com.doctorbookingapp.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public SignUpDto addUser(SignUpDto signUpDto) {
        // Check if user with the same username already exists
        if (userRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
            return null; // User already exists
        }

        // Create new user entity
        User userSignUp = new User();
        userSignUp.setFirstName(signUpDto.getFirstName());
        userSignUp.setLastName(signUpDto.getLastName());
        userSignUp.setUsername(signUpDto.getUsername());
        userSignUp.setEmail(signUpDto.getEmail());
        userSignUp.setPassword(BCrypt.hashpw(signUpDto.getPassword(), BCrypt.gensalt(10))); // Encrypt password
        userSignUp.setUserRole(signUpDto.getUserRole());

        // Save user to database
        User savedUser = userRepository.save(userSignUp);

        // Prepare DTO to return
        SignUpDto dto = new SignUpDto();
        dto.setId(savedUser.getId());
        dto.setFirstName(savedUser.getFirstName());
        dto.setLastName(savedUser.getLastName());
        dto.setUsername(savedUser.getUsername());
        dto.setUserRole(savedUser.getUserRole());
        // Do not return password for security reasons

        return dto;
    }

    @Override
    public String verifyLogin(SignInDto signInDto) {
        // Find user by username
        Optional<User> optionalUser = userRepository.findByUsername(signInDto.getUsername());
        if (optionalUser.isPresent()) {
            // User found, check password
            User user = optionalUser.get();
            if (BCrypt.checkpw(signInDto.getPassword(), user.getPassword())) {
                // Password matches, generate JWT token
                return jwtService.generateToken(user);
            }
        }
        // Either username doesn't exist or password doesn't match
        return null;
    }
}
